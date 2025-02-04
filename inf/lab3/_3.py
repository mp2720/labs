import re
from collections import namedtuple
from dataclasses import dataclass

@dataclass
class Adjective:
    base: str
    ending: str
    pos: int

def f(s: str, n: int) -> str:
    ending_pattern = r'ая|ее|яя|ий|ой|его|ому|ую|юю|ом|см|ему|им|ем|ое|ей|ого|ый|ым'
    adj_groups_dict: dict[str, list[Adjective]] = {}
    for m in re.findall(r'\b(\w+)(' + ending_pattern + r')\b', s):
        adj = Adjective(m.group(1), m.group(2), m.span()[0])
        if adj.base not in adj_groups_dict:
            adj_groups_dict[adj.base] = []
        adj_groups_dict[adj.base].append(adj)

    for adj_group in adj_groups_dict.values():
        if len(adj_group) < 2:
            continue

        m = max(n, len(adj_group) - 1)
        for adj in adj_group:
            

    return ''
