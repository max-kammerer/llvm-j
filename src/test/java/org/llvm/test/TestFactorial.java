package org.llvm.test;

import junit.framework.TestCase;

import org.llvm.BasicBlock;
import org.llvm.Builder;
import org.llvm.ExecutionEngine;
import org.llvm.GenericValue;
import org.llvm.LLVMException;
import org.llvm.Module;
import org.llvm.PassManager;
import org.llvm.Target;
import org.llvm.TypeRef;
import org.llvm.Value;
import org.llvm.binding.LLVMLibrary.LLVMCallConv;
import org.llvm.binding.LLVMLibrary.LLVMIntPredicate;

/**
 * Adapted from https://github.com/wickedchicken/llvm-c-example
 */
public class TestFactorial extends TestCase {

	public Value addFunction(Module mod) {
		Value fac = mod.addFunction("fac",
				TypeRef.functionType(TypeRef.int32Type(), TypeRef.int32Type()));
		fac.setFunctionCallConv(LLVMCallConv.LLVMCCallConv);
		Value n = fac.getParam(0);

		BasicBlock entry = fac.appendBasicBlock("entry");
		BasicBlock iftrue = fac.appendBasicBlock("iftrue");
		BasicBlock iffalse = fac.appendBasicBlock("iffalse");
		BasicBlock end = fac.appendBasicBlock("end");

		Builder builder = Builder.createBuilder();

		builder.positionBuilderAtEnd(entry);
		Value If = builder.buildICmp(LLVMIntPredicate.LLVMIntEQ, n, TypeRef
				.int32Type().constInt(0, false), "n == 0");
		builder.buildCondBr(If, iftrue, iffalse);

		builder.positionBuilderAtEnd(iftrue);
		Value res_iftrue = TypeRef.int32Type().constInt(1, false);
		builder.buildBr(end);

		builder.positionBuilderAtEnd(iffalse);
		Value n_minus = builder.buildSub(n,
				TypeRef.int32Type().constInt(1, false), "n - 1");
		Value call_fac = builder.buildCall(fac, "fac(n - 1)", n_minus);
		Value res_iffalse = builder.buildMul(n, call_fac, "n * fac(n - 1)");
		builder.buildBr(end);

		builder.positionBuilderAtEnd(end);
		Value res = builder.buildPhi(TypeRef.int32Type(), "result");
		Value phi_vals[] = {
			res_iftrue, res_iffalse
		};
		BasicBlock phi_blocks[] = {
			iftrue, iffalse
		};
		res.addIncoming(phi_vals, phi_blocks, 2);
		builder.buildRet(res);

		return fac;
	}

	public void testFactorial() {
		Target.initialiseNativeTarget();

		Module module = Module.createWithName("fac_module");
		Value fac = this.addFunction(module);

		ExecutionEngine engine = ExecutionEngine.createForModule(module);

		try {
			module.verify();

			/* Requires that the native JIT target was initialised beforehand. */
			engine.createJITCompilerForModule(module, 2);
		} catch (LLVMException e) {
			fail(e.getMessage());
		}

		PassManager pass = PassManager.create();
		engine.addTargetData(pass);

		pass.addConstantPropagationPass();
		pass.addInstructionCombiningPass();
		pass.addPromoteMemoryToRegisterPass();
		// pass.addDemoteMemoryToRegisterPass(); // Demotes every possible value to memory
		pass.addGVNPass();
		pass.addCFGSimplificationPass();

		pass.runForModule(module);

		module.dumpModule();

		GenericValue exec_res = engine.runFunction(fac,
				GenericValue.createInt(TypeRef.int32Type(), 6, false));
		assertEquals(720, exec_res.toInt(false));
	}

}
