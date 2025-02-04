type json_lex_states =
  | StStart
  (*| StNumber*)
  | StStr of json_lex_str_substates
  | StKeyword
  | StPunct of char
  | StInvalidChar of char

and json_lex_str_substates = StrBase | StrEscape | StrEnd

type json_lex_token_data =
  (*| TokenNumber of float*)
  | TokenString of string
  | TokenTrue
  | TokenFalse
  | TokenNull
  | TokenColon
  | TokenComma
  | TokenLeftBrace
  | TokenRightBrace
  | TokenLeftBracket
  | TokenRightBracket

(*type json_lex_token = json_lex_token_data * int*)

let json_lex_analyze str =
  let transition nf ch =
    match (nf, ch) with
    (* punctuation *)
    | StStart, (':' | ',' | '{' | '}' | '[' | ']' | '\n' | ' ' | '\t' | '\r') ->
        Some (StPunct ch)
    | StPunct _, _ -> None
    (* keyword *)
    | (StStart | StKeyword), ('a' .. 'z' | 'A' .. 'Z') -> Some StKeyword
    | StKeyword, _ -> None
    (* strings *)
    | StStart, '"' -> Some (StStr StrBase)
    | StStr StrBase, '"' -> Some (StStr StrEnd) (* конец строки *)
    | StStr StrBase, '\\' ->
        Some (StStr StrEscape) (* начало escape-последовательности *)
    | StStr StrEscape, _ ->
        Some (StStr StrBase) (* достаточно пропустить один символ *)
    | StStr StrBase, c when c != '\n' ->
        Some (StStr StrBase) (* все остальное, кроме '\n' *)
    | StStr StrEnd, _ -> None
    (* number *)
    (* invalid *)
    | _, _ -> Some (StInvalidChar ch)
  in

  let produce_token state text lineno =
    let no_nl_token token =
      Lexer.ValidToken { token = Some (token, lineno); is_newline = false }
    in

    (*Printf.printf "`%s` %d\n" text lineno;*)
    match state with
    | StStart -> Lexer.EOF
    | StInvalidChar c ->
        Lexer.InvalidToken { reason = Printf.sprintf "invalid character %c" c }
    | StKeyword -> (
        match text with
        | "true" -> no_nl_token TokenTrue
        | "false" -> no_nl_token TokenFalse
        | "null" -> no_nl_token TokenNull
        | _ ->
            Lexer.InvalidToken
              { reason = Printf.sprintf "invalid keyword %s" text })
    | StPunct c -> (
        match c with
        | ':' -> no_nl_token TokenColon
        | ',' -> no_nl_token TokenComma
        | '{' -> no_nl_token TokenRightBrace
        | '}' -> no_nl_token TokenLeftBrace
        | '[' -> no_nl_token TokenLeftBracket
        | ']' -> no_nl_token TokenRightBracket
        | '\n' -> Lexer.ValidToken { token = None; is_newline = true }
        | ' ' | '\t' | '\r' ->
            Lexer.ValidToken { token = None; is_newline = false }
        | _ ->
            Lexer.InvalidToken
              { reason = Printf.sprintf "invalid punctuation character %c" c })
    (*| StNumber -> no_nl_token (TokenNumber (Float.of_string text))*)
    | StStr StrEnd ->
        no_nl_token (TokenString (String.sub text 1 (String.length str - 2)))
    | StStr _ -> Lexer.InvalidToken { reason = Printf.sprintf "unclosed \"" }
    (*| _ -> Lexer.EOF*)
    (*| StateNumber, _ ->*)
  in

  let automaton = { Lexer.initial = StStart; transition } in
  let analyzer = { Lexer.automaton; Lexer.produce_token } in

  let result = Lexer.tokenize_all analyzer str in

  result

let () =
  let print_token token =
    let print_token_value = function
      (*| TokenNumber f -> Float.to_string f*)
      | TokenTrue -> "true"
      | TokenFalse -> "false"
      | TokenNull -> "null"
      | TokenColon -> ":"
      | TokenComma -> ","
      | TokenLeftBrace -> "{"
      | TokenRightBrace -> "}"
      | TokenLeftBracket -> "["
      | TokenRightBracket -> "]"
      | TokenString s -> s
    in

    let value, lineno = token in
    Printf.printf "%d `%s`\n" lineno (print_token_value value)
  in

  let s = {|"aa\\\\fasdfsdfksjdflsjd asfasdf

      |} in
  let result = json_lex_analyze s in

  match result with
  | Ok tokens -> List.iter print_token tokens
  | Error { lineno; msg } -> Printf.printf "error at %d: %s\n" lineno msg
