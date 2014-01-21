package org.llvm;

import static org.llvm.binding.LLVMTarget.LLVMInitializeX86Target;
import static org.llvm.binding.LLVMTarget.LLVMInitializeX86TargetInfo;
import static org.llvm.binding.LLVMTarget.LLVMInitializeX86TargetMC;

public class Target {
	/**
	 * The main program should call this function to
	 * initialize the native target corresponding to the host. This is useful
	 * for JIT applications to ensure that the target gets linked in correctly.
	 */
	public static void initialiseNativeTarget() {
		/* TODO For now let's assume that the host is always runnning x86. */
		LLVMInitializeX86TargetInfo();
		LLVMInitializeX86Target();
		LLVMInitializeX86TargetMC();
	}
}
