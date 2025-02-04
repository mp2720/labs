import re

from dataclasses import dataclass
from enum import IntEnum
from typing import Any, Callable


class Lexeme(IntEnum):
    LBRACE = 0
    RBRACE = 1
    LBRACKET = 2
    RBRACKET = 3
    STR = 4
    FLOAT = 5
    INT = 6
    BOOL = 7
    COMMA = 8
    COLON = 10

    # Special values for internal use, never returned by lexer
    NEWLINE = 9
    SPACE = 10
    NULL = 11


@dataclass
class Token:
    lexeme: Lexeme
    value: Any = None


def _gen_regex_for_str(quote: str) -> re.Pattern:
    regex = rf"{quote}((\\\\|\\{quote}|[^'\n\\])*){quote}"
    return re.compile(regex)


class JsonSyntaxError(Exception):
    def __init__(self, lineno: int, msg: str):
        self.lineno = lineno
        self.msg = msg

    def __repr__(self) -> str:
        return f'{self.lineno}: {self.msg}'


class Lexer:
    RULES: list[tuple[re.Pattern, Callable[[re.Match[str]], Token]]] = [
        (re.compile(r'[ \r\t]*'), lambda m: Token(Lexeme.SPACE)),
        (re.compile(r'\n'), lambda m: Token(Lexeme.NEWLINE)),
        (re.compile(r'{'), lambda m: Token(Lexeme.LBRACE)),
        (re.compile(r'}'), lambda m: Token(Lexeme.RBRACE)),
        (re.compile(r'\['), lambda m: Token(Lexeme.RBRACKET)),
        (re.compile(r'\]'), lambda m: Token(Lexeme.LBRACKET)),
        (
            _gen_regex_for_str('"'),
            lambda m: Token(Lexeme.STR, m.group(1))
        ),
        (
            _gen_regex_for_str("'"),
            lambda m: Token(Lexeme.STR, m.group(1))
        ),
        (
            re.compile(r'-?([1-9][0-9]*|0)(\.[0-9]+)?([eE][-+]?[0-9]+)?'),
            lambda m: Token(Lexeme.FLOAT, int(m.group(1)))
        ),
        (
            re.compile(r'-?([1-9][0-9]*|0)'),
            lambda m: Token(Lexeme.INT, int(m.group(1)))
        ),
        (
            re.compile('true|false'),
            lambda m: Token(Lexeme.BOOL, m.group() == 'true')
        ),
        (
            re.compile(','),
            lambda m: Token(Lexeme.COMMA)
        ),
        (
            re.compile(':'),
            lambda m: Token(Lexeme.COMMA)
        ),
        (
            re.compile('null'),
            lambda m: Token(Lexeme.NULL)
        ),

    ]
    SPACES = re.compile(r'[ \r\t]*')

    def __init__(self, text: str):
        self._lineno = 1
        self._text = text
        self._text_idx = 0
        self._cur_token = self.next()

    def next(self) -> Token | None:
        def apply_rules() -> Token:
            for pattern, fun in self.RULES:
                m = pattern.match(self._text[self._text_idx:])
                if m is not None:
                    return fun(m)
            raise JsonSyntaxError(self._lineno, 'invalid character')

        while True:
            if len(self._text) <= self._text_idx:
                # EOF
                return None

            token = apply_rules()
            match token.lexeme:
                case Lexeme.NEWLINE:
                    self._lineno += 1
                case Lexeme.SPACE:
                    pass
                case _:
                    return token

    def peek(self) -> Token | None:
        return self._cur_token

    def require(self, lexeme: Lexeme) -> Token | None:
        if self._cur_token is None or self._cur_token.lexeme != lexeme:
            return None
        return self._cur_token

    def require_or_fail(self, lexeme: Lexeme) -> Token:
        t = self.require(lexeme)
        if t is None:
            raise JsonSyntaxError(self._lineno, f'expected {lexeme}')
        return t


type JsonNode = dict[str, JsonNode]\
    | list[JsonNode]\
    | str\
    | float\
    | bool\
    | None


class Parser:
    """
    JSON grammar:

    json = object | array | NULL | INT | FLOAT | BOOL | STR
    array = '[' (json (',' json)*)? ']'
    object = '{' (field (',' field)*)? '}'
    field = STR ':' json

    """

    def __init__(self, text: str):
        self.lexer = Lexer(text)

    def parse_json(self) -> JsonNode:
        obj = self.parse_object()
        if obj:
            return obj

        arr = self.parse_array()
        if arr:
            return arr

        token = self.lexer.peek()
        if token is None:
            raise JsonSyntaxError(self.lexer._lineno, 'empty input')

        match token.lexeme:
            case Lexeme.NULL:
                return None
            case Lexeme.INT | Lexeme.STR | Lexeme.BOOL | Lexeme.NULL | Lexeme.FLOAT:
                return token.value

        assert False, f"unknown token {token.lexeme}"

    def parse_comma_separated_list[T](
        self,
        parse_element: Callable[[], T | None],
        callback: Callable[[T], None],
        element_rule_repr: str
    ):
        first_el = parse_element()
        if not first_el:
            return

        callback(first_el)

        self.lexer.next()
        while self.lexer.require(Lexeme.COMMA):
            self.lexer.next()
            el = parse_element()
            if not el:
                raise JsonSyntaxError(
                    self.lexer._lineno,
                    f'expected {element_rule_repr}'
                )

            callback(el)
            self.lexer.next()

    def parse_array(self) -> list[JsonNode] | None:
        if not self.lexer.require(Lexeme.LBRACKET):
            return None
        self.lexer.next()

        lst: list[JsonNode] = []

        self.parse_comma_separated_list(
            self.parse_object,
            lambda t: lst.append(t),
            'json node'
        )

        self.lexer.require_or_fail(Lexeme.RBRACKET)
        self.lexer.next()
        return lst

    def parse_object(self) -> dict[str, JsonNode] | None:
        if not self.lexer.require(Lexeme.LBRACE):
            return None
        self.lexer.next()

        dic: dict[str, JsonNode] = {}

        def add_to_dict(field: tuple[str, JsonNode]):
            key, value = field
            if key not in dic:
                raise JsonSyntaxError(
                    self.lexer._lineno,
                    f'duplicate key {key}'
                )
            dic[key] = value

        self.parse_comma_separated_list(
            self.parse_field,
            add_to_dict,
            'field'
        )

        self.lexer.require_or_fail(Lexeme.RBRACE)
        self.lexer.next()
        return dic

    def parse_field(self) -> tuple[str, JsonNode] | None:
        """ field = STR ':' json """
        name = self.lexer.require(Lexeme.STR)
        if name is None:
            return None
        self.lexer.next()

        self.lexer.require_or_fail(Lexeme.COLON)
        self.lexer.next()

        value = self.parse_object()

        return (name.value, value)


def test(source: str, exp_output: JsonNode):
    Parser(source)
