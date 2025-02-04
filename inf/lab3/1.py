import re
import sys

# %6=%4=2, %8=6

def f(s: str) -> int:
    return len(re.findall('X-{O', s))

assert f('X-{OX-{OX-{O') == 3
assert f('X-{Ox-{\n\n\n\t     \r\0OX-{O') == 2
assert f('asdflasdfjasldX-{Oxxxasfd') == 1
assert f('asdflasdfjasldX-{\nOxxxasfdX-{O\n') == 1
assert f('') == 0

print('\n' + str(f(sys.stdin.read())))
