import re

def f(s: str) -> tuple[bool, str | None]:
    lines = re.split(r'[\n/]', s)
    if len(lines) != 3:
        return False, 'Должно быть 3 строки.'
    return [len(re.findall('[аеоёуюяиыэ]', l, re.I)) for l in lines] == [5,7,5], None

assert f('Вечер за окном. / Еще один день прожит. / Жизнь скоротечна...') == (True, None)
assert f('Просто текст') == (False, 'Должно быть 3 строки.')
assert f('Просто текст') == (False, 'Должно быть 3 строки.')
assert f('Как вишня расцвела! / Она с коня согнала / И князя-гордеца.') ==\
        (False, None)

