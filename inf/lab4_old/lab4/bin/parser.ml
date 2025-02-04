type ('tokens, 'v, 'e) parser_result = Ok of 'v * 'tokens | Error of 'e

(** Конкатенация двух парсеров *)
let ( <*> ) a b (toks : 'toks) =
  match a toks with
  | Ok (ra, new_toks) -> (
      match b new_toks with
      | Ok (rb, new_toks) -> Ok ((ra, rb), new_toks)
      | Error e -> Error e)
  | Error e -> Error e

(** Объединение парсеров *)
let ( <|> ) a b (toks : 'toks) =
  match a toks with
  | Ok (_, _) as ret -> ret
  | Error _ -> b toks

let ( => ) p interpr toks =
  match p toks with
  | Ok (r, new_toks) -> Ok (interpr r, new_toks)
  | Error e -> Error e

let ( =>> ) p interpr = p => fun (l, r) -> interpr l r
let ret c _ = c

(* Строковой парсер *)
