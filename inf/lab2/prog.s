# Проверка 7,4 кода Хэмминга
# compile: gcc -c prog.s && ld prog.o && ./a.out

    .global _start

	.bss
buf:
	.zero	128

	.section    .rodata
invalid_input_msg:
    .string     "invalid input\n"
no_errors_msg:
    .string     "no errors found\n"
errors_msg:
    .string     "error found at bit "
newline:
    .string     "\n"

syndrome_tab:
    .byte   0
    .ascii  "  "
    .byte   0

    .byte   0
    .ascii  "r3"
    .byte   0

    .byte   0
    .ascii  "r2"
    .byte   0

    .byte   3
    .ascii  "i3"
    .byte   0

    .byte   0
    .ascii  "r1"
    .byte   0

    .byte   2
    .ascii  "i2"
    .byte   0

    .byte   1
    .ascii  "i1"
    .byte   0

    .byte   4
    .ascii  "i4"
    .byte   0

    .text

# ptr in %rdi
putstr:
    xorq    %rdx, %rdx
    movq    %rdi, %rsi

.loop:
    movzx   (%rdi), %rax
    incq    %rdi
    test    %al, %al
    jnz     .loop

    movq    %rdi, %rdx
    subq    %rsi, %rdx
    decq    %rdx
    movq    $1, %rdi
    movq    $1, %rax
    syscall
    ret

invalid_input:
    movq    $invalid_input_msg, %rdi
    call    putstr
    jmp     exit

# r12 - j1, r13 - j2, r14 - j3, r15 - j4
# rsi = buf
calc_syndrome:
    movzx   (%rsi,%r12), %rax
    xorb    (%rsi,%r13), %al
    xorb    (%rsi,%r14), %al
    xorb    (%rsi,%r15), %al
    ret

_start:
    movq    $0, %rax
    movq    $0, %rdi
    movq    $buf, %rsi
    movq    $128, %rdx
    syscall

    cmpq    $8, %rax
    jne     invalid_input

    addq    $6, %rsi

.bufloop:
    movzx   (%rsi), %rbx
    subb    $'0', %bl
    testb   %bl, %bl
    jz      .ok
    cmpb    $1, %bl
    jne     invalid_input

.ok:
    movb    %bl, (%rsi)
    cmpq    $buf, %rsi
    je      .bufloop_end
    decq    %rsi
    jmp     .bufloop

.bufloop_end:
    movq    $buf, %rsi

    # r1 r2 i1 r3 i2 i3 i4
    #  0  1  2  3  4  5  6

    # s1 = r1 ^ i1 ^ i2 ^ i4
    # s1 =  0    2    4    6
    movq    $0, %r12
    movq    $2, %r13
    movq    $4, %r14
    movq    $6, %r15
    call    calc_syndrome
    movb    %al, %bl

    # s2 = r2 ^ i1 ^ i3 ^ i4
    # s2 =  0    2    5    6
    movq    $1, %r12
    movq    $2, %r13
    movq    $5, %r14
    movq    $6, %r15
    call    calc_syndrome
    movb    %al, %bh

    # s3 = r3 ^ i2 ^ i3 ^ i4
    # s3 =  3    4    5    6
    movq    $3, %r12
    movq    $4, %r13
    movq    $5, %r14
    movq    $6, %r15
    call    calc_syndrome
    movb    %al, %cl

    shl     $2, %bl
    shl     $1, %bh
    xorq    %rax, %rax
    addb    %bl, %al
    addb    %bh, %al
    addb    %cl, %al
    movq    %rax, %r12

    # move info to buffer
    movb    buf+2, %al
    movb    buf+4, %ah
    movb    buf+5, %bl
    movb    buf+6, %bh
    movb    %al, buf
    movb    %ah, buf+1
    movb    %bl, buf+2
    movb    %bh, buf+3

    testq   %r12, %r12
    jz      .no_error

    movq    $errors_msg, %rdi
    call    putstr

    movq    %r12, %rsi

    leaq    syndrome_tab+1(,%r12,4), %rdi
    call    putstr

    movq    $newline, %rdi
    call    putstr

    # fix error
    movzx   syndrome_tab(,%r12,4), %rax
    decq    %rax
    movzx   buf(%rax), %rbx
    incq    %rbx
    andq    $1, %rbx
    movb    %bl, buf(%rax)

    jmp     .print_info

.no_error:
    movq    $no_errors_msg, %rdi
    call    putstr

.print_info:
    movb    $'0', %al
    addb    %al, buf
    addb    %al, buf+1
    addb    %al, buf+2
    addb    %al, buf+3
    movb    $'\n', %al
    movb    %al, buf+4
    xor     %rax, %rax
    movb    %al, buf+5
    movq    $buf, %rdi
    call    putstr

exit:
    # exit(0)
    mov     $60, %rax
    xor     %rdi, %rdi
    syscall


# 123456789012345
# 1110100
