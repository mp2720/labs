# Нега-двоичная
res = ""
value = int(input())

while True:
    rem = value % -10
    value = value // -10
    if rem < 0:
        rem = rem + 10
        value = value + 1

    res = str(rem) + res
    # if rem == 1:
    #     res = "1" + res
    # if rem == 0:
    #     res = "0" + res

    if value == 0:
        break

print(res)
