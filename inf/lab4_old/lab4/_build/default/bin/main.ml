open Parser

type tokens = { text : string; i : int; lineno : int }
type error = { lineno : int; reason : string }

let make_tokens text = { text; i = 0; lineno = 1 }
let getch toks = toks.text.[toks.i]
let err reason (toks : tokens) = Error { lineno = toks.lineno; reason }

let termch_if f toks =
  if toks.i >= String.length toks.text then err "unexpected EOF" toks
  else
    match getch toks with
    | c when f c -> Ok (c, { toks with i = toks.i + 1 })
    | c -> err (Printf.sprintf "unexpected `%c`" c) toks

let termch c = termch_if (( = ) c)
let empty z toks = Ok (z, toks)

let eof toks =
  if toks.i >= String.length toks.text then Ok ((), toks)
  else err "expected EOF" toks

let term s =
  let last_i = String.length s - 1 in
  let rec proc i =
    let c = termch s.[i] => String.make 1 in
    match i with
    | i when i = last_i -> c
    | _ -> c <*> proc (i + 1) =>> ( ^ )
  in
  proc 0

let make_str = String.make 1

let enclosed left p right =
  termch left <*> p <*> termch right => fun ((_, v), _) -> v

type json =
  | JsonNumber of float
  | JsonBool of bool
  | JsonNull
  | JsonString of string
  | JsonArray of json list
  | JsonObject of (string * json) list

let rec json toks =
  let jnumber =
    let digit_range i j =
      let pred c =
        let c = Char.lowercase_ascii c in
        i <= c && c <= j
      in
      termch_if pred => make_str
    in

    let sign with_plus =
      let p = termch '-' => ret "-" <|> empty "" in
      if with_plus then termch '+' => ret "+" <|> p else p
    in

    let zero = digit_range '0' '0' in
    let rec digits start toks =
      let digit = digit_range start '9' in
      (digit <*> (digit <*> digits start =>> ( ^ ) <|> empty "") =>> ( ^ )) toks
    in

    let int_part = zero <|> (digits '1' <*> digits '0' =>> ( ^ )) in
    let float_part = term "." <*> digits '0' =>> ( ^ ) in
    let e_part =
      let e = term "e" <|> term "E" in
      e <*> sign true <*> digits '0' => fun ((e, s), digs) -> e ^ s ^ digs
    in

    sign false
    <*> int_part
    <*> (float_part <|> empty "")
    <*> (e_part <|> empty "")
    => fun (((s, ip), fp), ep) ->
    JsonNumber (Float.of_string (s ^ ip ^ fp ^ ep))
  in

  let jbool =
    term "true" <|> term "false" => ( = ) "true" => fun x -> JsonBool x
  in
  let jnull = term "null" => ret JsonNull in

  let str =
    let termch_repl ec c = termch ec => ret c in
    let esc_char =
      termch_repl 'n' '\n'
      <|> termch_repl 'r' '\r'
      <|> termch_repl 't' '\t'
      <|> termch_repl 'f' '\x0c'
      <|> termch_repl 'b' '\b'
      <|> termch_repl '"' '"'
      <|> termch_repl '\\' '\\'
      <|> termch_repl '/' '/'
    in

    let esc = termch '\\' <*> esc_char => fun (_, c) -> make_str c in
    let reg = termch_if (fun c -> c != '"' && c != '\n') => make_str in
    let ch = esc <|> reg in

    let rec chars toks =
      let nonempty = ch <*> chars =>> ( ^ ) in
      (nonempty <|> empty "") toks
    in

    enclosed '"' chars '"'
  in

  let jstring = str => fun x -> JsonString x in

  let rec whitespace toks =
    let newline toks =
      let nl = termch '\n' toks in
      match nl with
      | Ok (r, new_toks) ->
          Ok (r, { new_toks with lineno = new_toks.lineno + 1 })
      | Error e -> Error e
    in

    let ws_char =
      termch ' ' <|> termch '\t' <|> termch '\r' <|> newline => ret ()
    in

    (ws_char <*> whitespace => fst <|> empty ()) toks
  in

  let compound left item right =
    let comma_item = termch ',' <*> item => snd in

    let rec comma_items toks =
      (comma_item <*> comma_items =>> List.cons) toks
    in

    let elements = item <*> comma_items =>> List.cons in

    enclosed left (elements <|> (whitespace => ret [])) right
  in

  let jarray = compound '[' json ']' => fun x -> JsonArray x in

  let jobject =
    let key = whitespace <*> str => snd in
    let item = key <*> termch ':' <*> json => fun ((k, _), v) -> (k, v) in
    compound '{' item '}' => fun x -> JsonObject x
  in

  let value = jnumber <|> jstring <|> jbool <|> jnull <|> jarray <|> jobject in
  (whitespace <*> value <*> whitespace <*> eof => fun (((_, v), _), _) -> v)
    toks
(*(compound '[' (termch '*') ']' => ret JsonNull) toks*)

(*let dump_json v l =*)
(*    function*)
(*  | JsonNumber f -> Printf.sprintf "%f"*)
(*  | JsonBool b -> Printf.sprintf "%b"*)
(*  | JsonNull -> "null"*)
(*  | JsonString s -> Printf.printf "\"%s\"" s*)
(*  | JsonArray arr -> "[\n" ^ List.fold dump_json *)
(*  | JsonObject of (string * json) list*)

let () =
  let s = read_line () in
  match make_tokens s |> json with
  | Error { lineno; reason } -> Printf.printf "%d %s" lineno reason
  | Ok (_, _) -> ()
