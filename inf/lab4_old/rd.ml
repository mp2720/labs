type token = 
    | String of string
    | Int of int
    | Float of float
    | Bool of bool
    | Null
    | LeftBrace
    | RightBrace
    | LeftBracket
    | RightBracket
    | Colon

type tokenizer = {
    source: string;
    index: int;
    lineno: int;
}

type tokenizer_result = 
    | Token of token
    | Error of int * string

let tokenize (t: tokenizer) =
    let read_char_seqs s out_token (t': tokenizer) =
        if String.starts_with ~prefix:s t'.source then
            String.length s, Some(out_token)
        else
            0, None
    in

    let read_colon = read_char_seqs ":" Colon in
    let read_left_brace = read_char_seqs "{" LeftBrace in
    let read_right_brace = read_char_seqs "}" RightBrace in
    let read_left_bracket = read_char_seqs "[" LeftBracket in
    let read_right_bracket = read_char_seqs "]" RightBracket in
    let read_null = read_char_seqs "null" Null in
    let read_true = read_char_seqs "true" (Bool true) in
    let read_true = read_char_seqs "false" (Bool false) in

    let read_char t' =
        t'.source.[t'.index] in

    let consume_char t' =
        {source=t'.source; index=t'.index + 1; lineno=t'.lineno} in

    let read_number t' =
        t'
        in
        

    t

(*let tokenize_all (t: tokenizer) = *)
(*    let tokenize_all' t' =*)
(**)
(**)
(*    tokenize_all'*)
