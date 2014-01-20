package org.llvm;

import static org.llvm.binding.LLVMLibrary.*;

import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.llvm.binding.LLVMLibrary.LLVMBasicBlockRef;
import org.llvm.binding.LLVMLibrary.LLVMBuilderRef;
import org.llvm.binding.LLVMLibrary.LLVMIntPredicate;
import org.llvm.binding.LLVMLibrary.LLVMOpcode;
import org.llvm.binding.LLVMLibrary.LLVMRealPredicate;
import org.llvm.binding.LLVMLibrary.LLVMTypeRef;
import org.llvm.binding.LLVMLibrary.LLVMValueRef;

/**
 * This provides a uniform API for creating instructions and inserting them into
 * a basic block: either at the end of a BasicBlock, or at a specific iterator
 * location in a block.
 */
public class Builder {
	private LLVMBuilderRef builder;

	LLVMBuilderRef builder() {
		return this.builder;
	}

	Builder(LLVMBuilderRef builder) {
		this.builder = builder;
	}

	@Override
	public void finalize() {
		this.dispose();
	}

	public void dispose() {
		LLVMDisposeBuilder(this.builder);
		this.builder = null;
	}

	/* Creation */

	public static Builder createBuilderInContext(Context c) {
		return new Builder(LLVMCreateBuilderInContext(c.context()));
	}

	public static Builder createBuilder() {
		return new Builder(LLVMCreateBuilder());
	}

	/* Navigation */

	public void positionBuilder(BasicBlock block, Value instr) {
		LLVMPositionBuilder(this.builder, block.bb(), instr.value());
	}

	public void positionBuilderBefore(Value instr) {
		LLVMPositionBuilderBefore(this.builder, instr.value());
	}

	public void positionBuilderAtEnd(BasicBlock block) {
		LLVMPositionBuilderAtEnd(this.builder, block.bb());
	}

	public BasicBlock getInsertBlock() {
		return new BasicBlock(LLVMGetInsertBlock(this.builder));
	}

	public void clearInsertionPosition() {
		LLVMClearInsertionPosition(this.builder);
	}

	public void insertIntoBuilder(Value instr) {
		LLVMInsertIntoBuilder(this.builder, instr.value());
	}

	public void insertIntoBuilderWithName(Value instr, String name) {
		LLVMInsertIntoBuilderWithName(this.builder, instr.value(),
				Pointer.pointerToCString(name));
	}

	public void setCurrentDebugLocation(Value l) {
		LLVMSetCurrentDebugLocation(this.builder, l.value());
	}

	public Value getCurrentDebugLocation() {
		return new Value(LLVMGetCurrentDebugLocation(this.builder));
	}

	public void setInstDebugLocation(Value inst) {
		LLVMSetInstDebugLocation(this.builder, inst.value());
	}

	/* Building */

	public Value buildRetVoid() {
		return new Value(LLVMBuildRetVoid(this.builder));
	}

	public Value buildRet(Value v) {
		return new Value(LLVMBuildRet(this.builder, v.value()));
	}

	public Value buildAggregateRet(Pointer<LLVMValueRef> retVals, int n) {
		return new Value(LLVMBuildAggregateRet(this.builder, retVals, n));
	}

	public Value buildBr(LLVMBasicBlockRef dest) {
		return new Value(LLVMBuildBr(this.builder, dest));
	}

	public Value buildBr(BasicBlock dest) {
		return new Value(LLVMBuildBr(this.builder, dest.bb()));
	}

	public Value buildCondBr(Value if_, LLVMBasicBlockRef then,
			LLVMBasicBlockRef else_) {
		return new Value(LLVMBuildCondBr(this.builder, if_.value(), then, else_));
	}

	public Value buildCondBr(Value if_, BasicBlock then, BasicBlock else_) {
		return new Value(LLVMBuildCondBr(this.builder, if_.value(), then.bb(),
				else_.bb()));
	}

	public Value buildSwitch(Value v, LLVMBasicBlockRef else_, int numCases) {
		return new Value(LLVMBuildSwitch(this.builder, v.value(), else_, numCases));
	}

	public Value buildIndirectBr(Value addr, int numDests) {
		return new Value(LLVMBuildIndirectBr(this.builder, addr.value(), numDests));
	}

	public Value buildInvoke(Value fn, Pointer<LLVMValueRef> args, int numArgs,
			LLVMBasicBlockRef then, LLVMBasicBlockRef catch_, String name) {
		return new Value(LLVMBuildInvoke(this.builder, fn.value(), args, numArgs,
				then, catch_, Pointer.pointerToCString(name)));
	}

	/* public Value buildUnwind() {
	 * return new Value(LLVMBuildUnwind(builder));
	 * } */

	public Value buildUnreachable() {
		return new Value(LLVMBuildUnreachable(this.builder));
	}

	// TODO: put these where they belong; they probably don't belong here.

	/**
	 * Add a case to the switch instruction
	 */
	public void addCase(Value switch_, Value onVal, LLVMBasicBlockRef dest) {
		LLVMAddCase(switch_.value(), onVal.value(), dest);
	}

	/**
	 * Add a destination to the indirectbr instruction
	 */
	public void addDestination(Value indirectBr, LLVMBasicBlockRef dest) {
		LLVMAddDestination(indirectBr.value(), dest);
	}

	public Value buildAdd(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildAdd(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildNSWAdd(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildNSWAdd(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildNUWAdd(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildNUWAdd(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildFAdd(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildFAdd(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildSub(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildSub(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildNSWSub(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildNSWSub(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildNUWSub(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildNUWSub(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildFSub(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildFSub(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildMul(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildMul(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildNSWMul(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildNSWMul(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildNUWMul(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildNUWMul(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildFMul(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildFMul(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildUDiv(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildUDiv(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildSDiv(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildSDiv(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildExactSDiv(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildExactSDiv(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildFDiv(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildFDiv(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildURem(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildURem(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildSRem(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildSRem(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildFRem(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildFRem(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildShl(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildShl(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildLShr(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildLShr(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildAShr(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildAShr(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildAnd(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildAnd(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildOr(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildOr(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildXor(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildXor(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildBinOp(IntValuedEnum<LLVMOpcode> op, Value lhs, Value rhs,
			String name) {
		return new Value(LLVMBuildBinOp(this.builder, op, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildNeg(Value v, String name) {
		return new Value(LLVMBuildNeg(this.builder, v.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildNSWNeg(Value v, String name) {
		return new Value(LLVMBuildNSWNeg(this.builder, v.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildNUWNeg(Value v, String name) {
		return new Value(LLVMBuildNUWNeg(this.builder, v.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildFNeg(Value v, String name) {
		return new Value(LLVMBuildFNeg(this.builder, v.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildNot(Value v, String name) {
		return new Value(LLVMBuildNot(this.builder, v.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildMalloc(LLVMTypeRef ty, String name) {
		return new Value(LLVMBuildMalloc(this.builder, ty,
				Pointer.pointerToCString(name)));
	}

	public Value buildArrayMalloc(LLVMTypeRef ty, Value val, String name) {
		return new Value(LLVMBuildArrayMalloc(this.builder, ty, val.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildAlloca(LLVMTypeRef ty, String name) {
		return new Value(LLVMBuildAlloca(this.builder, ty,
				Pointer.pointerToCString(name)));
	}

	public Value buildArrayAlloca(LLVMTypeRef ty, Value val, String name) {
		return new Value(LLVMBuildArrayAlloca(this.builder, ty, val.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildFree(Value pointerVal) {
		return new Value(LLVMBuildFree(this.builder, pointerVal.value()));
	}

	public Value buildLoad(Value pointerVal, String name) {
		return new Value(LLVMBuildLoad(this.builder, pointerVal.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildStore(Value val, Value ptr) {
		return new Value(LLVMBuildStore(this.builder, val.value(), ptr.value()));
	}

	public Value buildGEP(Value ptr, Pointer<LLVMValueRef> indices,
			int numIndices, String name) {
		return new Value(LLVMBuildGEP(this.builder, ptr.value(), indices,
				numIndices, Pointer.pointerToCString(name)));
	}

	public Value buildInBoundsGEP(Value ptr, Pointer<LLVMValueRef> indices,
			int numIndices, String name) {
		return new Value(LLVMBuildInBoundsGEP(this.builder, ptr.value(), indices,
				numIndices, Pointer.pointerToCString(name)));
	}

	public Value buildInBoundsGEP(Value ptr, String name, Value... indices) {
		return new Value(LLVMBuildInBoundsGEP(this.builder, ptr.value(),
				Value.internalize(indices), indices.length,
				Pointer.pointerToCString(name)));
	}

	public Value buildStructGEP(Value ptr, int idx, String name) {
		return new Value(LLVMBuildStructGEP(this.builder, ptr.value(), idx,
				Pointer.pointerToCString(name)));
	}

	public Value buildGlobalString(String str, String name) {
		return new Value(LLVMBuildGlobalString(this.builder,
				Pointer.pointerToCString(str), Pointer.pointerToCString(name)));
	}

	public Value buildGlobalStringPtr(String str, String name) {
		return new Value(LLVMBuildGlobalStringPtr(this.builder,
				Pointer.pointerToCString(str), Pointer.pointerToCString(name)));
	}

	public Value buildTrunc(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildTrunc(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildZExt(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildZExt(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildSExt(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildSExt(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildFPToUI(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildFPToUI(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildFPToSI(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildFPToSI(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildUIToFP(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildUIToFP(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildSIToFP(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildSIToFP(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildFPTrunc(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildFPTrunc(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildFPExt(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildFPExt(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildPtrToInt(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildPtrToInt(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildIntToPtr(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildIntToPtr(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildBitCast(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildBitCast(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildZExtOrBitCast(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildZExtOrBitCast(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildSExtOrBitCast(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildSExtOrBitCast(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildTruncOrBitCast(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildTruncOrBitCast(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildCast(IntValuedEnum<LLVMOpcode> op, Value val,
			LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildCast(this.builder, op, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildPointerCast(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildPointerCast(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	/**
	 * Signed cast.
	 */
	public Value buildIntCast(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildIntCast(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildFPCast(Value val, LLVMTypeRef destTy, String name) {
		return new Value(LLVMBuildFPCast(this.builder, val.value(), destTy,
				Pointer.pointerToCString(name)));
	}

	public Value buildICmp(IntValuedEnum<LLVMIntPredicate> op, Value lhs,
			Value rhs, String name) {
		return new Value(LLVMBuildICmp(this.builder, op, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildFCmp(IntValuedEnum<LLVMRealPredicate> op, Value lhs,
			Value rhs, String name) {
		return new Value(LLVMBuildFCmp(this.builder, op, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildPhi(LLVMTypeRef ty, String name) {
		return new Value(LLVMBuildPhi(this.builder, ty,
				Pointer.pointerToCString(name)));
	}

	public Value buildPhi(TypeRef ty, String name) {
		return new Value(LLVMBuildPhi(this.builder, ty.type(),
				Pointer.pointerToCString(name)));
	}

	public Value buildCall(Value fn, Pointer<LLVMValueRef> args, int numArgs,
			String name) {
		return new Value(LLVMBuildCall(this.builder, fn.value(), args, numArgs,
				Pointer.pointerToCString(name)));
	}

	public Value buildCall(Value fn, String name, Value... args) {
		return new Value(LLVMBuildCall(this.builder, fn.value(),
				Value.internalize(args), args.length,
				Pointer.pointerToCString(name)));
	}

	public Value buildSelect(Value if_, Value then, Value else_, String name) {
		return new Value(LLVMBuildSelect(this.builder, if_.value(), then.value(),
				else_.value(), Pointer.pointerToCString(name)));
	}

	public Value buildVAArg(Value list, LLVMTypeRef ty, String name) {
		return new Value(LLVMBuildVAArg(this.builder, list.value(), ty,
				Pointer.pointerToCString(name)));
	}

	public Value buildExtractElement(Value vecVal, Value index, String name) {
		return new Value(LLVMBuildExtractElement(this.builder, vecVal.value(),
				index.value(), Pointer.pointerToCString(name)));
	}

	public Value buildInsertElement(Value vecVal, Value eltVal, Value index,
			String name) {
		return new Value(LLVMBuildInsertElement(this.builder, vecVal.value(),
				eltVal.value(), index.value(), Pointer.pointerToCString(name)));
	}

	public Value buildShuffleVector(Value v1, Value v2, Value mask, String name) {
		return new Value(LLVMBuildShuffleVector(this.builder, v1.value(),
				v2.value(), mask.value(), Pointer.pointerToCString(name)));
	}

	public Value buildExtractValue(Value aggVal, int index, String name) {
		return new Value(LLVMBuildExtractValue(this.builder, aggVal.value(), index,
				Pointer.pointerToCString(name)));
	}

	public Value buildInsertValue(Value aggVal, Value eltVal, int index,
			String name) {
		return new Value(LLVMBuildInsertValue(this.builder, aggVal.value(),
				eltVal.value(), index, Pointer.pointerToCString(name)));
	}

	public Value buildIsNull(Value val, String name) {
		return new Value(LLVMBuildIsNull(this.builder, val.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildIsNotNull(Value val, String name) {
		return new Value(LLVMBuildIsNotNull(this.builder, val.value(),
				Pointer.pointerToCString(name)));
	}

	public Value buildPtrDiff(Value lhs, Value rhs, String name) {
		return new Value(LLVMBuildPtrDiff(this.builder, lhs.value(), rhs.value(),
				Pointer.pointerToCString(name)));
	}

}
