type transition = char -> bool


type state = {
    name: string;
    edges: edge list;
}
and edge =
    | ToParent of string * transition
