# 74    56 93 18 99 73
# 73

def t(x, i):
    return (x & (1 << (i-1))) >> (i-1)

def calc_sums74(x):
    r1 = t(x,1) ^ t(x,2) ^ t(x,4)
    r2 = t(x,1) ^ t(x,3) ^ t(x,4)
    r3 = t(x,2) ^ t(x,3) ^ t(x,4)
    return r1,r2,r3

def check74(x):
    x = bin(x)[2:][::-1]
    x = int(x,2)

    i1 = t(x, 3)
    i2 = t(x, 5)
    i3 = t(x, 6)
    i4 = t(x, 7)

    r1 = t(x,1)
    r2 = t(x,2)
    r3 = t(x,4)

    print(f'r1 r2 i1 r3 i2 i3 i4')
    print(f'{r1}  {r2}  {i1}  {r3}  {i2}  {i3}  {i4}\n')

    s1 = r1 ^ i1 ^ i2 ^ i4
    s2 = r2 ^ i1 ^ i3 ^ i4
    s3 = r3 ^ i2 ^ i3 ^ i4

    print('s1 = r1 ^ i1 ^ i2 ^ i4')
    print('s2 = r2 ^ i1 ^ i3 ^ i4')
    print('s3 = r3 ^ i2 ^ i3 ^ i4\n')

    print(f's1 = {r1} ^ {i1} ^ {i2} ^ {i4} = {s1}')
    print(f's2 = {r2} ^ {i1} ^ {i3} ^ {i4} = {s2}')
    print(f's3 = {r3} ^ {i2} ^ {i3} ^ {i4} = {s3}\n')

    errbit = {
        0b000: None,
        0b001: 'r3',
        0b010: 'r2',
        0b011: 'i3',
        0b100: 'r1',
        0b101: 'i2',
        0b110: 'i1',
        0b111: 'i4',
    }[s1*4 + s2*2 + s3]

    if not errbit:
        print('Синдром 000 -> ошибки нет')
        return

    print(f'Синдром {s1}{s2}{s3} -> ошибка в {errbit}')

def check1511(x):
    x = bin(x)[2:][::-1]
    x = int(x,2)

    r1 = t(x, 1)
    r2 = t(x, 2)
    i1 = t(x, 3)
    r3 = t(x, 4)
    i2 = t(x, 5)
    i3 = t(x, 6)
    i4 = t(x, 7)
    r4 = t(x, 8)
    i5 = t(x, 9)
    i6 = t(x, 10)
    i7 = t(x, 11)
    i8 = t(x, 12)
    i9 = t(x, 13)
    i10 = t(x, 14)
    i11 = t(x, 15)

    print('s1 = r1 ^ i1 ^ i2 ^ i4 ^ i5 ^ i7 ^ i9 ^ i11')
    print('s2 = r2 ^ i1 ^ i3 ^ i4 ^ i6 ^ i7 ^ i10 ^ i11')
    print('s3 = r3 ^ i2 ^ i3 ^ i4 ^ i8 ^ i9 ^ i10 ^ i11')
    print('s4 = r4 ^ i5 ^ i6 ^ i7 ^ i8 ^ i9 ^ i10 ^ i11')

    s1 = r1 ^ i1 ^ i2 ^ i4 ^ i5 ^ i7 ^ i9 ^ i11
    s2 = r2 ^ i1 ^ i3 ^ i4 ^ i6 ^ i7 ^ i10 ^ i11
    s3 = r3 ^ i2 ^ i3 ^ i4 ^ i8 ^ i9 ^ i10 ^ i11
    s4 = r4 ^ i5 ^ i6 ^ i7 ^ i8 ^ i9 ^ i10 ^ i11

    print(f's1 = {r1} ^ {i1} ^ {i2} ^ {i4} ^ {i5} ^ {i7} ^ {i9} ^ {i11} = {s1}')
    print(f's2 = {r2} ^ {i1} ^ {i3} ^ {i4} ^ {i6} ^ {i7} ^ {i10} ^ {i11} = {s2}')
    print(f's3 = {r3} ^ {i2} ^ {i3} ^ {i4} ^ {i8} ^ {i9} ^ {i10} ^ {i11} = {s3}')
    print(f's4 = {r4} ^ {i5} ^ {i6} ^ {i7} ^ {i8} ^ {i9} ^ {i10} ^ {i11} = {s4}')

    print(f'Синдром {s1}{s2}{s3}{s4}')

tasks = [
    ''
]

print(56)
check74(0b0011101)
print('\n')

print(93)
check74(0b1001110)
print('\n')

print(18)
check74(0b0100001)
print('\n')

print(99)
check74(0b0000111)
print('\n')

print(73)
check1511(0b001110010010100)

#  001 010 100
