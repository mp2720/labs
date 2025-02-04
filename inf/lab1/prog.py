gp = [0]*100
gp[0] = 0
for i in range(1,100):
    gp[i] = gp[i-1] * 7 + 3

def f(x, n):
    print(x, n)

    if x == 0:
        return ''

    g = gp[n]

    sign = -1 if x < 0 else 1
    x *= sign

    for c in range(4):
        p = c * 7**n 
        d = x - p
        if abs(d) <= g:
            if sign < 0 and c != 0:
                s = '{^' + str(c) + '}'
            else:
                s = str(c)

            if d == 0 and n != 0:
                return s + '0' * n

            return s + f(d * sign, n-1)

    return ''

# print(gp)

def h(x):
    if x == 0: return '0'

    for i in range(len(gp)):
        if abs(x) <= gp[i]:
            # print(i-1)
            return f(x, i - 1)

print(h(8134))
# print(h(931))
# h(8134)

# for x in range(-171, 172):
#     print(x, h(x))

print(gp[4])
