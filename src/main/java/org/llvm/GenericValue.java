package org.llvm;

import static org.llvm.binding.LLVMLibrary.LLVMCreateGenericValueOfFloat;
import static org.llvm.binding.LLVMLibrary.LLVMCreateGenericValueOfInt;
import static org.llvm.binding.LLVMLibrary.LLVMCreateGenericValueOfPointer;
import static org.llvm.binding.LLVMLibrary.LLVMGenericValueIntWidth;
import static org.llvm.binding.LLVMLibrary.LLVMGenericValueToFloat;
import static org.llvm.binding.LLVMLibrary.LLVMGenericValueToInt;
import static org.llvm.binding.LLVMLibrary.LLVMGenericValueToPointer;

import org.bridj.Pointer;
import org.llvm.binding.LLVMLibrary;

public class GenericValue {
	private LLVMLibrary.LLVMGenericValueRef ref;

	public LLVMLibrary.LLVMGenericValueRef ref() {
		return this.ref;
	}

	public GenericValue(LLVMLibrary.LLVMGenericValueRef ref) {
		this.ref = ref;
	}

	public void dispose() {
		if (this.ref != null) {
			LLVMLibrary.LLVMDisposeGenericValue(this.ref);
			this.ref = null;
		}
	}

	@Override
	public void finalize() {
		this.dispose();
	}

	public static GenericValue createInt(TypeRef ty, long N, boolean isSigned) {
		return new GenericValue(LLVMCreateGenericValueOfInt(ty.type(), N,
				isSigned ? 1 : 0));
	}

	public static GenericValue createPtr(Pointer<?> p) {
		return new GenericValue(LLVMCreateGenericValueOfPointer(p));
	}

	public static GenericValue createFloat(TypeRef ty, double n) {
		return new GenericValue(LLVMCreateGenericValueOfFloat(ty.type(), n));
	}

	public int intWidth() {
		return LLVMGenericValueIntWidth(this.ref);
	}

	public long toInt(boolean isSigned) {
		return LLVMGenericValueToInt(this.ref, isSigned ? 1 : 0);
	}

	public Pointer<?> toPointer() {
		return LLVMGenericValueToPointer(this.ref);
	}

	public double toFloat(TypeRef ty) {
		return LLVMGenericValueToFloat(ty.type(), this.ref);
	}

}
