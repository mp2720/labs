type 'state automaton = {
  initial : 'state;
  transition : 'state -> char -> 'state option;
}

type ('state, 'token) analyzer = {
  automaton : 'state automaton;
      (** Должно возвращать токен и флаг новой строки *)
  produce_token : 'state -> string -> int -> 'token produced_token;
}

and 'token produced_token =
  | ValidToken of { token : 'token option; is_newline : bool }
  | InvalidToken of { reason : string }
  | EOF

type 'token tokenizer_result =
  | Ok of 'token list
  | Error of { lineno : int; msg : string }

let tokenize_all analyzer str =
  let rec read_token index state =
    if String.length str <= index then (index, state)
    else
      match analyzer.automaton.transition state str.[index] with
      | None -> (index, state)
      | Some next_state -> read_token (index + 1) next_state
  in

  let rec read_all index lineno =
    let new_index, state = read_token index analyzer.automaton.initial in
    match
      analyzer.produce_token state
        (String.sub str index (new_index - index))
        lineno
    with
    | EOF -> Ok []
    | InvalidToken { reason } -> Error { lineno; msg = reason }
    | ValidToken { token; is_newline } -> (
        let lineno = lineno + if is_newline then 1 else 0 in
        match read_all new_index lineno with
        | Ok tokens -> (
            match token with None -> Ok tokens | Some t -> Ok (t :: tokens))
        | _ as err -> err)
  in

  read_all 0 1
