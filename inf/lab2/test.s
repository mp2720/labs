	.file	"test.c"
	.text
	.globl	halal
	.section	.rodata
.LC0:
	.string	"halal"
	.section	.data.rel.local,"aw"
	.align 8
	.type	halal, @object
	.size	halal, 8
halal:
	.quad	.LC0
	.text
	.globl	main
	.type	main, @function
main:
.LFB0:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	movl	$0, %eax
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	main, .-main
	.ident	"GCC: (GNU) 14.2.1 20240910"
	.section	.note.GNU-stack,"",@progbits
