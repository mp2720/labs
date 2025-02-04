import re

def f(s: str) -> list[str]:
    ret = []
    for match in re.finditer(r'\bВТ[\s\-:]+(?=((\w+[\s\-:]+){,4}ИТМО\b))', s):
        vt_pref = match.group(0)
        suf = match.group(1)
        for word in re.finditer(r'\bИТМО\b', suf):
            ret.append(vt_pref + suf[:word.span()[1]])
    return ret

# ыВТ ИТМО

assert f('ВТ ВТ ИТМО ИТМО') == ['ВТ ВТ ИТМО', 'ВТ ВТ ИТМО ИТМО', 'ВТ ИТМО', 'ВТ ИТМО ИТМО']
assert f('ВТ 1 2 3 4 5 ИТМО') == []
assert f('ВТ 1 ВТ ИТМО ИТМО') == ['ВТ 1 ВТ ИТМО', 'ВТ 1 ВТ ИТМО ИТМО', 'ВТ ИТМО', 'ВТ ИТМО ИТМО']
assert f('ИТМО ВТ') == []
assert f('ВТ ИТМО') == ['ВТ ИТМО']

print('\n'.join(f(input())))
