package org.llvm;

import static org.llvm.binding.LLVMLibrary.*;

import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.llvm.binding.LLVMLibrary.LLVMAttribute;
import org.llvm.binding.LLVMLibrary.LLVMBasicBlockRef;
import org.llvm.binding.LLVMLibrary.LLVMCallConv;
import org.llvm.binding.LLVMLibrary.LLVMIntPredicate;
import org.llvm.binding.LLVMLibrary.LLVMLinkage;
import org.llvm.binding.LLVMLibrary.LLVMOpcode;
import org.llvm.binding.LLVMLibrary.LLVMRealPredicate;
import org.llvm.binding.LLVMLibrary.LLVMUseRef;
import org.llvm.binding.LLVMLibrary.LLVMValueRef;
import org.llvm.binding.LLVMLibrary.LLVMVisibility;

import java.util.List;

/**
 * Represents an individual value in LLVM IR.
 */
public class Value {

	private final LLVMValueRef value;

	public LLVMValueRef value() {
		return this.value;
	}

	public Value(LLVMValueRef value) {
		this.value = value;
	}

	/**
	 * Obtain the type of a value.<br>
	 * 
	 * @see llvm::Value::getType()
	 */
	public TypeRef typeOf() {
		return new TypeRef(LLVMTypeOf(this.value));
	}

	/**
	 * Obtain the string name of a value.<br>
	 * 
	 * @see llvm::Value::getName()
	 */
	public String getValueName() {
		return LLVMGetValueName(this.value).getCString();
	}

	/**
	 * Set the string name of a value.<br>
	 * 
	 * @see llvm::Value::setName()
	 */
	public void setValueName(String name) {
		LLVMSetValueName(this.value, Pointer.pointerToCString(name));
	}

	/**
	 * Dump a representation of a value to stderr.<br>
	 * 
	 * @see llvm::Value::dump()
	 */
	public void dumpValue() {
		LLVMDumpValue(this.value);
	}

	/**
	 * Replace all uses of a value with another one.<br>
	 * 
	 * @see llvm::Value::replaceAllUsesWith()
	 */
	public void replaceAllUsesWith(Value newVal) {
		LLVMReplaceAllUsesWith(this.value, newVal.value());
	}

	/**
	 * Determine whether an instruction has any metadata attached.
	 */
	public int hasMetadata() {
		return LLVMHasMetadata(this.value);
	}

	/**
	 * Return metadata associated with an instruction value.
	 */
	public Value getMetadata(int kindID) {
		return new Value(LLVMGetMetadata(this.value, kindID));
	}

	/**
	 * Set metadata associated with an instruction value.
	 */
	public void setMetadata(int kindID, Value node) {
		LLVMSetMetadata(this.value, kindID, node.value());
	}

	/**
	 * Conversion functions. Return the input value if it is an instance of the
	 * specified class, otherwise NULL. See llvm::dyn_cast_or_null<>.
	 */
	public Value isArgument() {
		return new Value(LLVMIsAArgument(this.value));
	}

	public Value isABasicBlock() {
		return new Value(LLVMIsABasicBlock(this.value));
	}

	public Value isInlineAsm() {
		return new Value(LLVMIsAInlineAsm(this.value));
	}

	public Value isUser() {
		return new Value(LLVMIsAUser(this.value));
	}

	public Value isAConstant() {
		return new Value(LLVMIsAConstant(this.value));
	}

	public Value isConstantAggregateZero() {
		return new Value(LLVMIsAConstantAggregateZero(this.value));
	}

	public Value isConstantArray() {
		return new Value(LLVMIsAConstantArray(this.value));
	}

	public Value isConstantExpr() {
		return new Value(LLVMIsAConstantExpr(this.value));
	}

	public Value isConstantFP() {
		return new Value(LLVMIsAConstantFP(this.value));
	}

	public Value isConstantInt() {
		return new Value(LLVMIsAConstantInt(this.value));
	}

	public Value isConstantPointerNull() {
		return new Value(LLVMIsAConstantPointerNull(this.value));
	}

	public Value isConstantStruct() {
		return new Value(LLVMIsAConstantStruct(this.value));
	}

	public Value isConstantVector() {
		return new Value(LLVMIsAConstantVector(this.value));
	}

	public Value isGlobalValue() {
		return new Value(LLVMIsAGlobalValue(this.value));
	}

	public Value isFunction() {
		return new Value(LLVMIsAFunction(this.value));
	}

	public Value isGlobalAlias() {
		return new Value(LLVMIsAGlobalAlias(this.value));
	}

	public Value isGlobalVariable() {
		return new Value(LLVMIsAGlobalVariable(this.value));
	}

	public Value isUndefValue() {
		return new Value(LLVMIsAUndefValue(this.value));
	}

	public Value isInstruction() {
		return new Value(LLVMIsAInstruction(this.value));
	}

	public Value isBinaryOperator() {
		return new Value(LLVMIsABinaryOperator(this.value));
	}

	public Value isCallInst() {
		return new Value(LLVMIsACallInst(this.value));
	}

	public Value isIntrinsicInst() {
		return new Value(LLVMIsAIntrinsicInst(this.value));
	}

	public Value isDbgInfoIntrinsic() {
		return new Value(LLVMIsADbgInfoIntrinsic(this.value));
	}

	public Value isDbgDeclareInst() {
		return new Value(LLVMIsADbgDeclareInst(this.value));
	}

	//	public Value isEHSelectorInst() { return new Value(LLVMIsAEHSelectorInst(value)); }
	public Value isMemIntrinsic() {
		return new Value(LLVMIsAMemIntrinsic(this.value));
	}

	public Value isMemCpyInst() {
		return new Value(LLVMIsAMemCpyInst(this.value));
	}

	public Value isMemMoveInst() {
		return new Value(LLVMIsAMemMoveInst(this.value));
	}

	public Value isMemSetInst() {
		return new Value(LLVMIsAMemSetInst(this.value));
	}

	public Value isCmpInst() {
		return new Value(LLVMIsACmpInst(this.value));
	}

	public Value isFCmpInst() {
		return new Value(LLVMIsAFCmpInst(this.value));
	}

	public Value isICmpInst() {
		return new Value(LLVMIsAICmpInst(this.value));
	}

	public Value isExtractElementInst() {
		return new Value(LLVMIsAExtractElementInst(this.value));
	}

	public Value isGetElementPtrInst() {
		return new Value(LLVMIsAGetElementPtrInst(this.value));
	}

	public Value isInsertElementInst() {
		return new Value(LLVMIsAInsertElementInst(this.value));
	}

	public Value isInsertValueInst() {
		return new Value(LLVMIsAInsertValueInst(this.value));
	}

	public Value isPHINode() {
		return new Value(LLVMIsAPHINode(this.value));
	}

	public Value isSelectInst() {
		return new Value(LLVMIsASelectInst(this.value));
	}

	public Value isShuffleVectorInst() {
		return new Value(LLVMIsAShuffleVectorInst(this.value));
	}

	public Value isStoreInst() {
		return new Value(LLVMIsAStoreInst(this.value));
	}

	public Value isTerminatorInst() {
		return new Value(LLVMIsATerminatorInst(this.value));
	}

	public Value isBranchInst() {
		return new Value(LLVMIsABranchInst(this.value));
	}

	public Value isInvokeInst() {
		return new Value(LLVMIsAInvokeInst(this.value));
	}

	public Value isReturnInst() {
		return new Value(LLVMIsAReturnInst(this.value));
	}

	public Value isSwitchInst() {
		return new Value(LLVMIsASwitchInst(this.value));
	}

	public Value isUnreachableInst() {
		return new Value(LLVMIsAUnreachableInst(this.value));
	}

	//	public Value isUnwindInst() { return new Value(LLVMIsAUnwindInst(value)); }
	public Value isUnaryInstruction() {
		return new Value(LLVMIsAUnaryInstruction(this.value));
	}

	public Value isAllocaInst() {
		return new Value(LLVMIsAAllocaInst(this.value));
	}

	public Value isCastInst() {
		return new Value(LLVMIsACastInst(this.value));
	}

	public Value isBitCastInst() {
		return new Value(LLVMIsABitCastInst(this.value));
	}

	public Value isFPExtInst() {
		return new Value(LLVMIsAFPExtInst(this.value));
	}

	public Value isFPToSIInst() {
		return new Value(LLVMIsAFPToSIInst(this.value));
	}

	public Value isFPToUIInst() {
		return new Value(LLVMIsAFPToUIInst(this.value));
	}

	public Value isFPTruncInst() {
		return new Value(LLVMIsAFPTruncInst(this.value));
	}

	public Value isIntToPtrInst() {
		return new Value(LLVMIsAIntToPtrInst(this.value));
	}

	public Value isPtrToIntInst() {
		return new Value(LLVMIsAPtrToIntInst(this.value));
	}

	public Value isSExtInst() {
		return new Value(LLVMIsASExtInst(this.value));
	}

	public Value isSIToFPInst() {
		return new Value(LLVMIsASIToFPInst(this.value));
	}

	public Value isTruncInst() {
		return new Value(LLVMIsATruncInst(this.value));
	}

	public Value isUIToFPInst() {
		return new Value(LLVMIsAUIToFPInst(this.value));
	}

	public Value isZExtInst() {
		return new Value(LLVMIsAZExtInst(this.value));
	}

	public Value isExtractValueInst() {
		return new Value(LLVMIsAExtractValueInst(this.value));
	}

	public Value isLoadInst() {
		return new Value(LLVMIsALoadInst(this.value));
	}

	public Value isVAArgInst() {
		return new Value(LLVMIsAVAArgInst(this.value));
	}

	/**
	 * Determine whether the specified constant instance is constant.
	 */
	public boolean isConstant() {
		return LLVMIsConstant(this.value) != 0;
	}

	/**
	 * Determine whether a value instance is null.<br>
	 * 
	 * @see llvm::Constant::isNullValue()
	 */
	public boolean isNull() {
		return LLVMIsNull(this.value) != 0;
	}

	/**
	 * Determine whether a value instance is undefined.
	 */
	public boolean isUndef() {
		return LLVMIsUndef(this.value) != 0;
	}

	// TODO: move
	public static native LLVMUseRef LLVMGetFirstUse(LLVMValueRef val);

	// TODO: move
	public static native LLVMUseRef LLVMGetNextUse(LLVMUseRef u);

	/**
	 * Obtain the user value for a user.<br>
	 * The returned value corresponds to a llvm::User type.<br>
	 * 
	 * @see llvm::Use::getUser()
	 */
	// TODO: move
	public static Value getUser(LLVMUseRef u) {
		return new Value(LLVMGetUser(u));
	}

	/**
	 * Obtain the value this use corresponds to.<br>
	 * 
	 * @see llvm::Use::get()
	 */
	// TODO: move
	public static Value getUsedValue(LLVMUseRef u) {
		return new Value(LLVMGetUsedValue(u));
	}

	/**
	 * Obtain an operand at a specific index in a llvm::User value.<br>
	 * 
	 * @see llvm::User::getOperand()
	 */
	public Value getOperand(int index) {
		return new Value(LLVMGetOperand(this.value, index));
	}

	/**
	 * Set an operand at a specific index in a llvm::User value.<br>
	 * 
	 * @see llvm::User::setOperand()
	 */
	public void setOperand(int index, Value val) {
		LLVMSetOperand(this.value, index, val.value());
	}

	/**
	 * Obtain the number of operands in a llvm::User value.<br>
	 * 
	 * @see llvm::User::getNumOperands()
	 */
	public int getNumOperands() {
		return LLVMGetNumOperands(this.value);
	}

	// MetaData
	/* public ValueRef MDStringInContext(LLVMContextRef c, Pointer<Byte> str,
	 * int sLen) {
	 * return new ValueRef(LLVMMDStringInContext(value));
	 * }
	 * public ValueRef MDString(Pointer<Byte> str, int sLen) {
	 * return new ValueRef(LLVMMDString(value));
	 * }
	 * public ValueRef MDNodeInContext(LLVMContextRef c,
	 * Pointer<LLVMValueRef> vals, int count) {
	 * return new ValueRef(LLVMMDNodeInContext(value));
	 * }
	 * public ValueRef MDNode(Pointer<LLVMValueRef> vals, int count) {
	 * return new ValueRef(LLVMMDNode(value));
	 * } */

	/**
	 * Obtain the zero extended value for an integer constant value.<br>
	 * 
	 * @see llvm::ConstantInt::getZExtValue()
	 */
	public long constIntGetZExtValue() {
		return LLVMConstIntGetZExtValue(this.value);
	}

	/**
	 * Obtain the sign extended value for an integer constant value.<br>
	 * 
	 * @see llvm::ConstantInt::getSExtValue()
	 */
	public long constIntGetSExtValue() {
		return LLVMConstIntGetSExtValue(this.value);
	}

	/**
	 * Create a ConstantDataSequential and initialize it with a string.<br>
	 * 
	 * @see llvm::ConstantDataArray::getString()
	 */
	public static Value constStringInContext(Context c, String str, int length,
			boolean dontNullTerminate) {
		return new Value(LLVMConstStringInContext(c.context(),
				Pointer.pointerToCString(str), length, dontNullTerminate ? 1
																		: 0));
	}

	/* public ValueRef constStructInContext(Context c, ValueRef[] constantVals,
	 * int count, boolean packed) {
	 * return new ValueRef(LLVMConstStructInContext(C.context(), constantVals,
	 * count, packed ? 1 : 0));
	 * } */

	/**
	 * Create a ConstantDataSequential with string content in the global
	 * context.<br>
	 * This is the same as LLVMConstStringInContext except it operates on the<br>
	 * global context.<br>
	 * 
	 * @see LLVMConstStringInContext()<br>
	 * @see llvm::ConstantDataArray::getString()
	 */
	public static Value constString(String str, int length,
			boolean dontNulTerminate) {
		return new Value(LLVMConstString(Pointer.pointerToCString(str), length,
				dontNulTerminate ? 1 : 0));
	}

	public static Value constString(String str) {
		return constString(str, str.length(), false);
	}

	/**
	 * Create a ConstantArray from values.<br>
	 * 
	 * @see llvm::ConstantArray::get()
	 */
	public static Value constArray(TypeRef elementTy, List<Value> constantVals) {
		return new Value(LLVMConstArray(elementTy.type(),
				internalize(constantVals), constantVals.size()));
	}

	/**
	 * Create a ConstantStruct in the global Context.<br>
	 * This is the same as LLVMConstStructInContext except it operates on the<br>
	 * global Context.<br>
	 * 
	 * @see LLVMConstStructInContext()
	 */
	// TODO: change Pointer to array
	public static Value constStruct(Pointer<LLVMValueRef> constantVals,
			int count, boolean packed) {
		return new Value(LLVMConstStruct(constantVals, count, packed ? 1 : 0));
	}

	/**
	 * Create a non-packed constant structure in the global context.
	 */
	public static Value constStruct(Value... constantVals) {
		return new Value(LLVMConstStruct(internalize(constantVals),
				constantVals.length, 0));
	}

	/**
	 * Create a non-anonymous ConstantStruct from values.
	 */
	public static Value constNamedStruct(TypeRef structType,
			Value... constantVals) {
		return new Value(LLVMConstNamedStruct(structType.type(),
				internalize(constantVals), constantVals.length));
	}

	/**
	 * Create a ConstantVector from values.<br>
	 * 
	 * @see llvm::ConstantVector::get()
	 */
	// TODO: change Pointer to array
	public static Value constVector(Pointer<LLVMValueRef> scalarConstantVals,
			int size) {
		return new Value(LLVMConstVector(scalarConstantVals, size));
	}

	public static native IntValuedEnum<LLVMOpcode> GetConstOpcode(
			LLVMValueRef constantVal);

	public Value constNeg() {
		return new Value(LLVMConstNeg(this.value));
	}

	public Value constNSWNeg() {
		return new Value(LLVMConstNSWNeg(this.value));
	}

	public Value constNUWNeg() {
		return new Value(LLVMConstNUWNeg(this.value));
	}

	public Value constFNeg() {
		return new Value(LLVMConstFNeg(this.value));
	}

	public Value constNot() {
		return new Value(LLVMConstNot(this.value));
	}

	public static Value constAdd(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstAdd(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constNSWAdd(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstNSWAdd(lhsConstant.value(),
				rhsConstant.value()));
	}

	public static Value constNUWAdd(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstNUWAdd(lhsConstant.value(),
				rhsConstant.value()));
	}

	public static Value constFAdd(Value lhsConstant, Value rhsConstant) {
		return new Value(
				LLVMConstFAdd(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constSub(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstSub(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constNSWSub(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstNSWSub(lhsConstant.value(),
				rhsConstant.value()));
	}

	public static Value constNUWSub(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstNUWSub(lhsConstant.value(),
				rhsConstant.value()));
	}

	public static Value constFSub(Value lhsConstant, Value rhsConstant) {
		return new Value(
				LLVMConstFSub(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constMul(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstMul(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constNSWMul(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstNSWMul(lhsConstant.value(),
				rhsConstant.value()));
	}

	public static Value constNUWMul(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstNUWMul(lhsConstant.value(),
				rhsConstant.value()));
	}

	public static Value constFMul(Value lhsConstant, Value rhsConstant) {
		return new Value(
				LLVMConstFMul(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constUDiv(Value lhsConstant, Value rhsConstant) {
		return new Value(
				LLVMConstUDiv(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constSDiv(Value lhsConstant, Value rhsConstant) {
		return new Value(
				LLVMConstSDiv(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constExactSDiv(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstExactSDiv(lhsConstant.value(),
				rhsConstant.value()));
	}

	public static Value constFDiv(Value lhsConstant, Value rhsConstant) {
		return new Value(
				LLVMConstFDiv(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constURem(Value lhsConstant, Value rhsConstant) {
		return new Value(
				LLVMConstURem(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constSRem(Value lhsConstant, Value rhsConstant) {
		return new Value(
				LLVMConstSRem(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constFRem(Value lhsConstant, Value rhsConstant) {
		return new Value(
				LLVMConstFRem(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constAnd(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstAnd(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constOr(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstOr(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constXor(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstXor(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constICmp(IntValuedEnum<LLVMIntPredicate> predicate,
			Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstICmp(predicate, lhsConstant.value(),
				rhsConstant.value()));
	}

	public static Value constFCmp(IntValuedEnum<LLVMRealPredicate> predicate,
			Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstFCmp(predicate, lhsConstant.value(),
				rhsConstant.value()));
	}

	public static Value constShl(Value lhsConstant, Value rhsConstant) {
		return new Value(LLVMConstShl(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constLShr(Value lhsConstant, Value rhsConstant) {
		return new Value(
				LLVMConstLShr(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constAShr(Value lhsConstant, Value rhsConstant) {
		return new Value(
				LLVMConstAShr(lhsConstant.value(), rhsConstant.value()));
	}

	public static Value constGEP(Value constantVal, List<Integer> indices) {
		Pointer ptrIndices = Pointer.allocateTypedPointers(LLVMValueRef.class,
				indices.size());

		int i = 0;
		for (Integer index : indices) {
			LLVMValueRef valueRef = TypeRef.int32Type().constInt(index, false)
					.value();
			ptrIndices.set(i, valueRef);
			i++;
		}

		return new Value(LLVMConstGEP(constantVal.value(), ptrIndices,
				indices.size()));
	}

	public static Value constInBoundsGEP(Value constantVal,
			Pointer<LLVMValueRef> constantIndices, int numIndices) {
		return new Value(LLVMConstInBoundsGEP(constantVal.value(),
				constantIndices, numIndices));
	}

	public static Value constInBoundsGEP(Value constantVal,
			Value... constantIndices) {
		return new Value(LLVMConstInBoundsGEP(constantVal.value(),
				internalize(constantIndices), constantIndices.length));
	}

	public static Value constTrunc(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstTrunc(constantVal.value(), toType.type()));
	}

	public static Value constSExt(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstSExt(constantVal.value(), toType.type()));
	}

	public static Value constZExt(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstZExt(constantVal.value(), toType.type()));
	}

	public static Value constFPTrunc(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstFPTrunc(constantVal.value(), toType.type()));
	}

	public static Value constFPExt(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstFPExt(constantVal.value(), toType.type()));
	}

	public static Value constUIToFP(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstUIToFP(constantVal.value(), toType.type()));
	}

	public static Value constSIToFP(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstSIToFP(constantVal.value(), toType.type()));
	}

	public static Value constFPToUI(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstFPToUI(constantVal.value(), toType.type()));
	}

	public static Value constFPToSI(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstFPToSI(constantVal.value(), toType.type()));
	}

	public static Value constPtrToInt(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstPtrToInt(constantVal.value(), toType.type()));
	}

	public static Value constIntToPtr(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstIntToPtr(constantVal.value(), toType.type()));
	}

	public static Value constBitCast(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstBitCast(constantVal.value(), toType.type()));
	}

	public static Value constZExtOrBitCast(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstZExtOrBitCast(constantVal.value(),
				toType.type()));
	}

	public static Value constSExtOrBitCast(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstSExtOrBitCast(constantVal.value(),
				toType.type()));
	}

	public static Value constTruncOrBitCast(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstTruncOrBitCast(constantVal.value(),
				toType.type()));
	}

	public static Value constPointerCast(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstPointerCast(constantVal.value(),
				toType.type()));
	}

	public static Value constIntCast(Value constantVal, TypeRef toType,
			boolean isSigned) {
		return new Value(LLVMConstIntCast(constantVal.value(), toType.type(),
				isSigned ? 1 : 0));
	}

	public static Value constFPCast(Value constantVal, TypeRef toType) {
		return new Value(LLVMConstFPCast(constantVal.value(), toType.type()));
	}

	public static Value constSelect(Value constantCondition,
			Value constantIfTrue, Value constantIfFalse) {
		return new Value(LLVMConstSelect(constantCondition.value(),
				constantIfTrue.value(), constantIfFalse.value()));
	}

	public static Value constExtractElement(Value vectorConstant,
			Value indexConstant) {
		return new Value(LLVMConstExtractElement(vectorConstant.value(),
				indexConstant.value()));
	}

	public static Value constInsertElement(Value vectorConstant,
			Value elementValueConstant, Value indexConstant) {
		return new Value(LLVMConstInsertElement(vectorConstant.value(),
				elementValueConstant.value(), indexConstant.value()));
	}

	public static Value constShuffleVector(Value vectorAConstant,
			Value vectorBConstant, Value maskConstant) {
		return new Value(LLVMConstShuffleVector(vectorAConstant.value(),
				vectorBConstant.value(), maskConstant.value()));
	}

	public static Value constExtractValue(Value aggConstant,
			Pointer<Integer> idxList, int numIdx) {
		return new Value(LLVMConstExtractValue(aggConstant.value(), idxList,
				numIdx));
	}

	public static Value constInsertValue(Value aggConstant,
			Value elementValueConstant, Pointer<Integer> idxList, int numIdx) {
		return new Value(LLVMConstInsertValue(aggConstant.value(),
				elementValueConstant.value(), idxList, numIdx));
	}

	public static Value constInlineAsm(TypeRef ty, String asmString,
			String constraints, boolean hasSideEffects, boolean isAlignStack) {
		return new Value(LLVMConstInlineAsm(ty.type(),
				Pointer.pointerToCString(asmString),
				Pointer.pointerToCString(constraints), hasSideEffects ? 1 : 0,
				isAlignStack ? 1 : 0));
	}

	public static Value blockAddress(Value f, BasicBlock bb) {
		return new Value(LLVMBlockAddress(f.value(), bb.bb()));
	}

	public Module getGlobalParent() {
		return new Module(LLVMGetGlobalParent(this.value));
	}

	public boolean isDeclaration() {
		return LLVMIsDeclaration(this.value) != 0;
	}

	public IntValuedEnum<LLVMLinkage> getLinkage() {
		return LLVMGetLinkage(this.value);
	}

	public void setLinkage(IntValuedEnum<LLVMLinkage> linkage) {
		LLVMSetLinkage(this.value, linkage);
	}

	public String getSection() {
		return LLVMGetSection(this.value).getCString();
	}

	public void setSection(String section) {
		LLVMSetSection(this.value, Pointer.pointerToCString(section));
	}

	public IntValuedEnum<LLVMVisibility> getVisibility() {
		return LLVMGetVisibility(this.value);
	}

	public void setVisibility(IntValuedEnum<LLVMVisibility> viz) {
		LLVMSetVisibility(this.value, viz);
	}

	public void getAlignment() {
		LLVMGetAlignment(this.value);
	}

	public void setAlignment(int bytes) {
		LLVMSetAlignment(this.value, bytes);
	}

	// this.value is GlobalVar
	public Value getNextGlobal() {
		return new Value(LLVMGetNextGlobal(this.value));
	}

	public Value getPreviousGlobal() {
		return new Value(LLVMGetPreviousGlobal(this.value));
	}

	// this.value is GlobalVar
	public void deleteGlobal() {
		LLVMDeleteGlobal(this.value);
	}

	public Value getInitializer() {
		return new Value(LLVMGetInitializer(this.value));
	}

	public void setInitializer(Value constantVal) {
		LLVMSetInitializer(this.value, constantVal.value());
	}

	public boolean isThreadLocal() {
		return LLVMIsThreadLocal(this.value) != 0;
	}

	public void setThreadLocal(boolean isThreadLocal) {
		LLVMSetThreadLocal(this.value, isThreadLocal ? 1 : 0);
	}

	public boolean isGlobalConstant() {
		return LLVMIsGlobalConstant(this.value) != 0;
	}

	public void setGlobalConstant(boolean isConstant) {
		LLVMSetGlobalConstant(this.value, isConstant ? 1 : 0);
	}

	/**
	 * Advance a Function iterator to the next Function.<br>
	 * Returns null if the iterator was already at the end and there are no more<br>
	 * functions.
	 */
	public Value getNextFunction() {
		return new Value(LLVMGetNextFunction(this.value));
	}

	/**
	 * Decrement a Function iterator to the previous Function.<br>
	 * Returns null if the iterator was already at the beginning and there are<br>
	 * no previous functions.
	 */
	public Value getPreviousFunction() {
		return new Value(LLVMGetPreviousFunction(this.value));
	}

	/**
	 * Remove a function from its containing module and deletes it.<br>
	 * 
	 * @see llvm::Function::eraseFromParent()
	 */
	public void deleteFunction() {
		LLVMDeleteFunction(this.value);
	}

	/**
	 * Obtain the ID number from a function instance.<br>
	 * 
	 * @see llvm::Function::getIntrinsicID()
	 */
	public void getIntrinsicID() {
		LLVMGetIntrinsicID(this.value);
	}

	/**
	 * Obtain the calling function of a function.<br>
	 * The returned value corresponds to the LLVMCallConv enumeration.<br>
	 * 
	 * @see llvm::Function::getCallingConv()
	 */
	public void getFunctionCallConv() {
		LLVMGetFunctionCallConv(this.value);
	}

	/**
	 * Set the calling convention of a function.<br>
	 * 
	 * @see llvm::Function::setCallingConv()<br>
	 * @param cc
	 *        LLVMCallConv to set calling convention to
	 */
	public void setFunctionCallConv(LLVMCallConv cc) {
		LLVMSetFunctionCallConv(this.value, (int) cc.value());
	}

	/**
	 * Obtain the name of the garbage collector to use during code<br>
	 * generation.<br>
	 * 
	 * @see llvm::Function::getGC()
	 */
	public String getGC() {
		return LLVMGetGC(this.value).getCString();
	}

	/**
	 * Define the garbage collector to use during code generation.<br>
	 * 
	 * @see llvm::Function::setGC()
	 */
	public void setGC(String name) {
		LLVMSetGC(this.value, Pointer.pointerToCString(name));
	}

	/**
	 * Add an attribute to a function.<br>
	 * 
	 * @see llvm::Function::addAttribute()
	 */
	public void addFunctionAttr(IntValuedEnum<LLVMAttribute> pa) {
		LLVMAddFunctionAttr(this.value, pa);
	}

	/**
	 * Obtain an attribute from a function.<br>
	 * 
	 * @see llvm::Function::getAttributes()
	 */
	public IntValuedEnum<LLVMAttribute> getFunctionAttr() {
		return LLVMGetFunctionAttr(this.value);
	}

	/**
	 * Remove an attribute from a function.
	 */
	public void removeFunctionAttr(IntValuedEnum<LLVMAttribute> pa) {
		LLVMRemoveFunctionAttr(this.value, pa);
	}

	/**
	 * Obtain the number of parameters in a function.<br>
	 * 
	 * @see llvm::Function::arg_size()
	 */
	public int countParams() {
		return LLVMCountParams(this.value);
	}

	/**
	 * Obtain the parameters in a function.<br>
	 * The takes a pointer to a pre-allocated array of LLVMValueRef that is<br>
	 * at least LLVMCountParams() long. This array will be filled with<br>
	 * LLVMValueRef instances which correspond to the parameters the<br>
	 * function receives. Each LLVMValueRef corresponds to a llvm::Argument<br>
	 * instance.<br>
	 * 
	 * @see llvm::Function::arg_begin()
	 */
	public void getParams(Pointer<LLVMValueRef> params) {
		LLVMGetParams(this.value, params);
	}

	/**
	 * Obtain the parameter at the specified index.<br>
	 * Parameters are indexed from 0.<br>
	 * 
	 * @see llvm::Function::arg_begin()
	 */
	public Value getParam(int index) {
		return new Value(LLVMGetParam(this.value, index));
	}

	/**
	 * Obtain the function to which this argument belongs.<br>
	 * Unlike other functions in this group, this one takes a LLVMValueRef<br>
	 * that corresponds to a llvm::Attribute.<br>
	 * The returned LLVMValueRef is the llvm::Function to which this<br>
	 * argument belongs.
	 */
	public Value getParamParent() {
		return new Value(LLVMGetParamParent(this.value));
	}

	/**
	 * Obtain the first parameter to a function.<br>
	 * 
	 * @see llvm::Function::arg_begin()
	 */
	public Value getFirstParam() {
		return new Value(LLVMGetFirstParam(this.value));
	}

	/**
	 * Obtain the last parameter to a function.<br>
	 * 
	 * @see llvm::Function::arg_end()
	 */
	public Value getLastParam() {
		return new Value(LLVMGetLastParam(this.value));
	}

	/**
	 * Obtain the next parameter to a function.<br>
	 * This takes a LLVMValueRef obtained from LLVMGetFirstParam() (which is<br>
	 * actually a wrapped iterator) and obtains the next parameter from the<br>
	 * underlying iterator.
	 */
	public Value getNextParam() {
		return new Value(LLVMGetNextParam(this.value));
	}

	/**
	 * Obtain the previous parameter to a function.<br>
	 * This is the opposite of LLVMGetNextParam().
	 */
	public Value getPreviousParam() {
		return new Value(LLVMGetPreviousParam(this.value));
	}

	/**
	 * Add an attribute to a function argument.<br>
	 * 
	 * @see llvm::Argument::addAttr()
	 */
	public void addAttribute(IntValuedEnum<LLVMAttribute> pa) {
		LLVMAddAttribute(this.value, pa);
	}

	/**
	 * Remove an attribute from a function argument.<br>
	 * 
	 * @see llvm::Argument::removeAttr()
	 */
	public void removeAttribute(IntValuedEnum<LLVMAttribute> pa) {
		LLVMRemoveAttribute(this.value, pa);
	}

	/**
	 * Get an attribute from a function argument.
	 */
	public IntValuedEnum<LLVMAttribute> getAttribute() {
		return LLVMGetAttribute(this.value);
	}

	/**
	 * Set the alignment for a function parameter.<br>
	 * 
	 * @see llvm::Argument::addAttr()<br>
	 * @see llvm::Attribute::constructAlignmentFromInt()
	 */
	public void setParamAlignment(int align) {
		LLVMSetParamAlignment(this.value, align);
	}

	/**
	 * Determine whether a LLVMValueRef is itself a basic block.
	 */
	public boolean isBasicBlock() {
		return LLVMValueIsBasicBlock(this.value) != 0;
	}

	/**
	 * Convert a LLVMValueRef to a LLVMBasicBlockRef instance.
	 */
	public BasicBlock asBasicBlock() {
		return new BasicBlock(LLVMValueAsBasicBlock(this.value));
	}

	/**
	 * Obtain the number of basic blocks in a function.<br>
	 * 
	 * @param Fn
	 *        Function value to operate on.
	 */
	public int countBasicBlocks() {
		return LLVMCountBasicBlocks(this.value);
	}

	/**
	 * Obtain all of the basic blocks in a function.<br>
	 * This operates on a function value. The BasicBlocks parameter is a<br>
	 * pointer to a pre-allocated array of LLVMBasicBlockRef of at least<br>
	 * LLVMCountBasicBlocks() in length. This array is populated with<br>
	 * LLVMBasicBlockRef instances.
	 */
	public void getBasicBlocks(Pointer<LLVMBasicBlockRef> basicBlocks) {
		LLVMGetBasicBlocks(this.value, basicBlocks);
	}

	/**
	 * Obtain the first basic block in a function.<br>
	 * The returned basic block can be used as an iterator. You will likely<br>
	 * eventually call into LLVMGetNextBasicBlock() with it.<br>
	 * 
	 * @see llvm::Function::begin()
	 */
	public BasicBlock getFirstBasicBlock() {
		return new BasicBlock(LLVMGetFirstBasicBlock(this.value));
	}

	/**
	 * Obtain the last basic block in a function.<br>
	 * 
	 * @see llvm::Function::end()
	 */
	public BasicBlock getLastBasicBlock() {
		return new BasicBlock(LLVMGetLastBasicBlock(this.value));
	}

	/**
	 * Obtain the basic block that corresponds to the entry point of a<br>
	 * function.<br>
	 * 
	 * @see llvm::Function::getEntryBlock()
	 */
	public BasicBlock getEntryBasicBlock() {
		return new BasicBlock(LLVMGetEntryBasicBlock(this.value));
	}

	/**
	 * Append a basic block to the end of a function.<br>
	 * 
	 * @see llvm::BasicBlock::Create()
	 */
	public BasicBlock appendBasicBlockInContext(Context c, String name) {
		return new BasicBlock(LLVMAppendBasicBlockInContext(c.context(),
				this.value, Pointer.pointerToCString(name)));
	}

	/**
	 * Append a basic block to the end of a function using the global<br>
	 * context.<br>
	 * 
	 * @see llvm::BasicBlock::Create()
	 */
	public BasicBlock appendBasicBlock(String name) {
		return new BasicBlock(LLVMAppendBasicBlock(this.value,
				Pointer.pointerToCString(name)));
	}

	// Instruction

	/**
	 * Obtain the basic block to which an instruction belongs.<br>
	 * 
	 * @see llvm::Instruction::getParent()
	 */
	public BasicBlock getInstructionParent() {
		return new BasicBlock(LLVMGetInstructionParent(this.value));
	}

	/**
	 * Obtain the instruction that occurs after the one specified.<br>
	 * The next instruction will be from the same basic block.<br>
	 * If this is the last instruction in a basic block, NULL will be<br>
	 * returned.
	 */
	public Value getNextInstruction(Value inst) {
		return new Value(LLVMGetNextInstruction(this.value));
	}

	/**
	 * Obtain the instruction that occured before this one.<br>
	 * If the instruction is the first instruction in a basic block, NULL<br>
	 * will be returned.
	 */
	public Value getPreviousInstruction(Value inst) {
		return new Value(LLVMGetPreviousInstruction(this.value));
	}

	/**
	 * Set the calling convention for a call instruction.<br>
	 * This expects an LLVMValueRef that corresponds to a llvm::CallInst or<br>
	 * llvm::InvokeInst.
	 */
	public void setInstructionCallConv(int cc) {
		LLVMSetInstructionCallConv(this.value, cc);
	}

	/**
	 * Obtain the calling convention for a call instruction.<br>
	 * This is the opposite of LLVMSetInstructionCallConv(). Reads its<br>
	 * usage.
	 */
	public int getInstructionCallConv() {
		return LLVMGetInstructionCallConv(this.value);
	}

	public void addInstrAttribute(int index,
			IntValuedEnum<LLVMAttribute> attribute) {
		LLVMAddInstrAttribute(this.value, index, attribute);
	}

	public void removeInstrAttribute(int index,
			IntValuedEnum<LLVMAttribute> attribute) {
		LLVMRemoveInstrAttribute(this.value, index, attribute);
	}

	public void setInstrParamAlignment(int index, int align) {
		LLVMSetInstrParamAlignment(this.value, index, align);
	}

	/**
	 * Obtain whether a call instruction is a tail call.<br>
	 * This only works on llvm::CallInst instructions.<br>
	 * 
	 * @see llvm::CallInst::isTailCall()
	 */
	public boolean isTailCall() {
		return LLVMIsTailCall(this.value) != 0;
	}

	/**
	 * Set whether a call instruction is a tail call.<br>
	 * This only works on llvm::CallInst instructions.<br>
	 * 
	 * @see llvm::CallInst::setTailCall()
	 */
	public void setTailCall(boolean isTailCall) {
		LLVMSetTailCall(this.value, isTailCall ? 1 : 0);
	}

	/**
	 * Add an incoming value to the end of a PHI list.
	 */
	public void addIncoming(Value[] incomingValues,
			BasicBlock[] incomingBlocks, int count) {

		LLVMValueRef[] rawVals = new LLVMValueRef[incomingValues.length];
		for (int i = 0; i < incomingValues.length; i++) {
			rawVals[i] = incomingValues[i].value;
		}
		Pointer<LLVMValueRef> ptrVals = Pointer.pointerToArray(rawVals);

		LLVMBasicBlockRef[] rawBlocks = new LLVMBasicBlockRef[incomingBlocks.length];
		for (int i = 0; i < incomingBlocks.length; i++) {
			rawBlocks[i] = incomingBlocks[i].bb();
		}
		Pointer<LLVMBasicBlockRef> ptrBlocks = Pointer
				.pointerToArray(rawBlocks);

		LLVMAddIncoming(this.value, ptrVals, ptrBlocks, count);
	}

	public void addClause(Value clauseValue) {
		LLVMAddClause(this.value, clauseValue.value);
	}

	public void setCleanup(boolean value) {
		LLVMSetCleanup(this.value, value ? 1 : 0);
	}

	/**
	 * Obtain the number of incoming basic blocks to a PHI node.
	 */
	public int countIncoming() {
		return LLVMCountIncoming(this.value);
	}

	/**
	 * Obtain an incoming value to a PHI node as a LLVMValueRef.
	 */
	public Value getIncomingValue(int index) {
		return new Value(LLVMGetIncomingValue(this.value, index));
	}

	/**
	 * Obtain an incoming value to a PHI node as a LLVMBasicBlockRef.
	 */
	public BasicBlock getIncomingBlock(int index) {
		return new BasicBlock(LLVMGetIncomingBlock(this.value, index));
	}

	static Pointer<LLVMValueRef> internalize(Value[] values) {
		int n = values.length;
		LLVMValueRef[] inner = new LLVMValueRef[n];
		for (int i = 0; i < n; i++) {
			inner[i] = values[i].value;
		}

		Pointer<LLVMValueRef> array = Pointer.allocateTypedPointers(
				LLVMValueRef.class, values.length);
		if (array == null) {
			return null;
		}
		array.setArray(inner);

		return array;
	}

	static Pointer<LLVMValueRef> internalize(List<Value> values) {
		int n = values.size();
		LLVMValueRef[] inner = new LLVMValueRef[n];
		for (int i = 0; i < n; i++) {
			inner[i] = values.get(i).value;
		}

		Pointer<LLVMValueRef> array = Pointer.allocateTypedPointers(
				LLVMValueRef.class, values.size());
		if (array == null) {
			return null;
		}
		array.setArray(inner);

		return array;
	}

}