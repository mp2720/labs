open Parser

(*let str_to_list s =*)
(*  let rec process i =*)
(*    if i >= String.length s then [] else s.[i] :: process (i + 1)*)
(*  in*)
(*  process 0*)

type tokens = { text : string; i : int; lineno : int }
type error = { lineno : int; reason : string }

let getch toks = toks.text.[toks.i]
let err reason (toks : tokens) = Error { lineno = toks.lineno; reason }

let termch_if f toks =
  if toks.i >= String.length toks.text then err "unexpected EOF" toks
  else
    match getch toks with
    | c when f c -> Ok (c, { toks with i = toks.i + 1 })
    | c -> err (Printf.sprintf "expected `%c`" c) toks

let termch c = termch_if (( = ) c)
let termchs chars = termch_if (fun x -> List.exists (( = ) x) chars)

(*let empty z toks = Ok (z, toks)*)

(*let many e z sem_comb grammar = (grammar %* z => sem_comb) %| (e) => sem_comb*)

let term s =
  let last_i = String.length s - 1 in
  let rec proc i =
    let c = termch s.[i] => String.make 1 in
    match i with
    | i when i = last_i -> c
    | _ -> c %* proc (i + 1) => fun (s1, s2) -> s1 ^ s2
  in
  proc 0

type json =
  | JsonNull
  | JsonBool of bool
  | JsonNumber of float
  | JsonString of string
  | JsonArray of json list
  | JsonObject of (string * json) list

let jbool = term "true" %| term "false" => fun x -> JsonBool (x = "true")
let jnull = term "null" => fun _ -> JsonNull

let digit_range i j =
  termch_if (fun c -> i <= c && c <= j) => fun c -> Char.code c - Char.code i

let dec_digit = digit_range '0' '9'
let hex_digit = digit_range 'a' 'b' %| digit_range 'A' 'B'

let jstring =
  let simple_esc = termchs [ 'n'; 't'; 'r'; 'b'; 'f'; '"'; '\\'; '/' ] in

  let hex_esc =
    termch 'u' %* hex_digit %* hex_digit %* hex_digit %* hex_digit
    => fun ((((_, d1), d2), d3), d4) ->
    Utf (d1 * 65536) + (d2 * 4096) + (d3 * 256) + (d4 * 16) + d4
  in

  let esc = termch '\\' %* hex_esc => snd in

  0

(*let unescape_str s =*)
(*    (* если 0, то это неправильная esc последователность *)*)
(*    let esc_mode_to_min_len = function*)
(*        | 'b' | 'f' | 'n' | 'r' | 't' | '"' | '\\' | '/' -> 1*)
(*        | 'u' -> 4*)
(*        | _ -> 0*)
(**)
(*  let rec unesc i = 0 in*)
(**)
(*  let rec proc i =*)
(*    if String.length s <= i then ""*)
(*    else*)
(*      match s.[i] with*)
(*      | '\\' -> unesc (i + 1)*)
(*      | _ -> String.make 1 s.[i] ^ proc (i + 1)*)
(*  in*)
(*  proc 0*)
