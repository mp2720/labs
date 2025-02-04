# Перевод из 7С СС в 10 СС.
# compile: gcc -c prog.s && ld prog.o && ./a.out
# неправильный ввод - UB

    .global _start


	.bss
buf:
	.zero	128

    .text
_start:
    # read str
    movq    $0, %rax
    movq    $0, %rdi
    movq    $buf, %rsi
    movq    $128, %rdx
    syscall

    # set up
    subq    $2, %rax
    movq    $buf, %rsi
    addq    %rax, %rsi

    # rbx - p, rcx - sum, rsi - strptr
    xorq    %rcx, %rcx
    movq    $1, %rbx

.readlp:
    movzx   (%rsi), %rax

    cmpb    $'}', %al
    jne     .simpledig

    # skip '}'
    decq    %rsi

    movzx   (%rsi), %rax
    subq    $'0', %rax
    neg     %rax 

    # skip '{^'
    subq    $2, %rsi

    jmp     .cont

.simpledig:
    subq    $'0', %rax

.cont:
    mulq    %rbx            # ans in %rax
    addq    %rax, %rcx

    movq    $4, %rax
    mulq    %rbx            # ans in %rax
    movq    %rax, %rbx

    cmpq    $buf, %rsi
    jz      .printstart

    decq    %rsi
    jmp     .readlp
    
.printstart:
    # neg flag
    xorq    %r12, %r12

    testq   %rcx, %rcx
    jns      .nonneg

    incq    %r12
    neg     %rcx

.nonneg:
    movq    %rcx, %rax
    leaq    buf+126, %rsi
    movq    $10, %rbx
    
    # rax - num, rsi - strptr, rbx - const 10

.prinlp:
    xor     %rdx, %rdx
    divq    %rbx
    addq    $'0', %rdx
    movb    %dl, (%rsi)
    decq    %rsi
    test    %rax, %rax
    jnz     .prinlp

    movb    $'\n', buf+127

    testq   %r12, %r12
    jz      .printend

    movb    $'-', (%rsi)
    decq    %rsi

.printend:
    incq    %rsi
    movq    $1, %rax
    movq    $1, %rdi
    leaq    buf+128, %rdx
    subq    %rsi, %rdx
    syscall

    # exit(0)
    mov     $60, %rax               
    xor     %rdi, %rdi              
    syscall                         

