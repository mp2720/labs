import re


def f(s, n):
    ending_pattern = 'ая|ее|яя|ий|ой|его|ому|ую|юю|ом|см|ему|им|ем|ое|ей|ого|ый|ым|ых|ые'
    replaced_bases = set()
    for adj in re.finditer(rf'\b(\w+)({ending_pattern})\b', s):
        base = adj.group(1)
        if base in replaced_bases:
            continue
        nth_form = base + adj.group(2)
        i = 0
        for adj_form in re.finditer(rf'\b{base}({ending_pattern})\b', s, re.IGNORECASE):
            nth_form = adj_form.group(0)
            i += 1
            if i >= n:
                break
        s = re.sub(rf'\b{base}({ending_pattern})\b',
                   nth_form, s, flags=re.IGNORECASE)
        replaced_bases.add(base)
    return s


assert (f("ХХый ХХая ХХее УУому", 1) == 'ХХый ХХый ХХый УУому')
assert (f("ХХый ХХая ХХее УУому", 2) == 'ХХая ХХая ХХая УУому')
assert (f("ZZого", 1000) == 'ZZого')
assert (f("ее ого Хого ых Хому", 2) == "ее ого Хому ых Хому")
assert (f("ААийЕЕ ААийЕЕей ААийЕЕому", 2) == "ААийЕЕ ААийЕЕому ААийЕЕому")
print(f("Ленивый Быстрый Ленивые Большой Ленивая", 1))

# n = int(input())
# print(f(input(), n))
