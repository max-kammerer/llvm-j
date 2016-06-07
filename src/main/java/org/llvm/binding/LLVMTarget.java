package org.llvm.binding;

import org.bridj.BridJ;
import org.bridj.ann.Library;

/**
 * This file was manually created as llvm-c/Target.h uses macro hacks
 * to dynamically generate the prototypes for the architecture-specific
 * initialisation functions. Only a subset is declared here as to maintain
 * compatibility with future LLVM versions and non-standard compilation settings.
 */
@Library("LLVM-3.8")
public class LLVMTarget {
	static {
		BridJ.register();
	}
	public native static void LLVMInitializeX86TargetInfo();
	public native static void LLVMInitializeX86Target();
	public native static void LLVMInitializeX86TargetMC();
	public native static void LLVMInitializeX86AsmPrinter();
	public native static void LLVMInitializeX86AsmParser();
	public native static void LLVMInitializeX86Disassembler();
}
