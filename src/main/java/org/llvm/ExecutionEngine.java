package org.llvm;

import static org.llvm.binding.LLVMLibrary.LLVMAddGlobalMapping;
import static org.llvm.binding.LLVMLibrary.LLVMAddModule;
import static org.llvm.binding.LLVMLibrary.LLVMAddTargetData;
import static org.llvm.binding.LLVMLibrary.LLVMCreateExecutionEngineForModule;
import static org.llvm.binding.LLVMLibrary.LLVMCreateJITCompilerForModule;
import static org.llvm.binding.LLVMLibrary.LLVMDisposeExecutionEngine;
import static org.llvm.binding.LLVMLibrary.LLVMDisposeMessage;
import static org.llvm.binding.LLVMLibrary.LLVMFindFunction;
import static org.llvm.binding.LLVMLibrary.LLVMFreeMachineCodeForFunction;
import static org.llvm.binding.LLVMLibrary.LLVMGetExecutionEngineTargetData;
import static org.llvm.binding.LLVMLibrary.LLVMGetPointerToGlobal;
import static org.llvm.binding.LLVMLibrary.LLVMRecompileAndRelinkFunction;
import static org.llvm.binding.LLVMLibrary.LLVMRemoveModule;
import static org.llvm.binding.LLVMLibrary.LLVMRunFunction;
import static org.llvm.binding.LLVMLibrary.LLVMRunFunctionAsMain;
import static org.llvm.binding.LLVMLibrary.LLVMRunStaticConstructors;
import static org.llvm.binding.LLVMLibrary.LLVMRunStaticDestructors;

import org.bridj.Pointer;
import org.llvm.binding.LLVMLibrary.*;

import static org.llvm.binding.LLVMLibrary.*;

/**
 * Implements various analyses of the LLVM IR.
 */
public class ExecutionEngine {

	private final LLVMExecutionEngineRef engine;

	LLVMExecutionEngineRef engine() {
		return this.engine;
	}

	ExecutionEngine(LLVMExecutionEngineRef engine) {
		this.engine = engine;
	}

	public void finalise() {
		this.dispose();
	}

	public void dispose() {
		LLVMDisposeExecutionEngine(this.engine);
	}

	public static ExecutionEngine createForModule(Module m) {
		Pointer<Pointer<Byte>> outError = Pointer.allocateBytes(1, 1024);

		Pointer<LLVMExecutionEngineRef> pEE = Pointer
				.allocateTypedPointer(LLVMExecutionEngineRef.class);

		boolean err = LLVMCreateExecutionEngineForModule(pEE, m.module(),
				outError) != 0;
		if (err) {
			String msg = outError.get().getCString();
			throw new RuntimeException("can't create execution engine: " + msg);
		}

		return new ExecutionEngine(pEE.get());
	}

	/* public static native int LLVMCreateInterpreterForModule(
	 * Pointer<Pointer<LLVMOpaqueExecutionEngine>> outInterp,
	 * LLVMModuleRef m, Pointer<Pointer<Byte>> outError);
	 * public static native int LLVMCreateJITCompilerForModule(
	 * Pointer<Pointer<LLVMOpaqueExecutionEngine>> outJIT,
	 * LLVMModuleRef m, int optLevel, Pointer<Pointer<Byte>> outError);
	 * public static native int LLVMCreateExecutionEngine(
	 * Pointer<Pointer<LLVMOpaqueExecutionEngine>> outEE,
	 * LLVMModuleProviderRef mp, Pointer<Pointer<Byte>> outError);
	 * public static native int LLVMCreateInterpreter(
	 * Pointer<Pointer<LLVMOpaqueExecutionEngine>> outInterp,
	 * LLVMModuleProviderRef mp, Pointer<Pointer<Byte>> outError);
	 * public static native int LLVMCreateJITCompiler(
	 * Pointer<Pointer<LLVMOpaqueExecutionEngine>> outJIT,
	 * LLVMModuleProviderRef mp, int optLevel,
	 * Pointer<Pointer<Byte>> outError); */

	public void createJITCompilerForModule(Module m, int optLevel)
			throws LLVMException {
		Pointer<Pointer<Byte>> ppByte = Pointer.pointerToCStrings("");
		Pointer<LLVMExecutionEngineRef> pExec = Pointer
				.allocate(LLVMExecutionEngineRef.class);
		pExec.set(this.engine);
		int retval = LLVMCreateJITCompilerForModule(pExec, m.module(),
				optLevel, ppByte);
		if (retval != 0) {
			Pointer<Byte> pByte = ppByte.getPointer(Byte.class);
			final String message = pByte.getCString();
			LLVMDisposeMessage(pByte);
			throw new LLVMException(message);
		}
	}

	public void runStaticConstructors() {
		LLVMRunStaticConstructors(this.engine);
	}

	public void runStaticDestructors() {
		LLVMRunStaticDestructors(this.engine);
	}

	public boolean runFunctionAsMain(Value f, int argC,
			Pointer<Pointer<Byte>> argV, Pointer<Pointer<Byte>> envP) {
		return LLVMRunFunctionAsMain(this.engine, f.value(), argC, argV, envP) != 0;
	}

	public GenericValue runFunction(Value f, GenericValue... args) {
		// Pointer<Pointer<LLVMOpaqueGenericValue>> args) {

		return new GenericValue(LLVMRunFunction(this.engine, f.value(),
				args.length, internalize(args)));
	}

	public void freeMachineCodeForFunction(Value f) {
		LLVMFreeMachineCodeForFunction(this.engine, f.value());
	}

	public void addModule(Module m) {
		LLVMAddModule(this.engine, m.module());
	}

	public Module removeModule(Module m) {
		Pointer<Pointer<Byte>> outError = Pointer.allocateBytes(1, 1024);
		Pointer<LLVMModuleRef> outMod = Pointer
				.allocateTypedPointer(LLVMModuleRef.class);
		boolean err = LLVMRemoveModule(this.engine, m.module(), outMod,
				outError) != 0;
		if (err) {
			String msg = outError.get().getCString();
			throw new RuntimeException("can't remove module: " + msg);
		}
		return new Module(outMod.get());
	}

	public Value findFunction(String name) { // Pointer<Pointer<LLVMOpaqueValue>> outFn) {
		Pointer<Byte> cstr = Pointer.pointerToCString(name);
		Pointer<LLVMValueRef> outFn = Pointer
				.allocateTypedPointer(LLVMValueRef.class);
		boolean err = LLVMFindFunction(this.engine, cstr, outFn) != 0;
		if (err) {
			throw new RuntimeException("LLVMFindFunction can't find " + name);
		}
		return new Value(outFn.get());
	}

	// TODO: this probably is returning a ValueRef for the recompiled Fn
	public Pointer<?> recompileAndRelinkFunction(Value fn) {
		return LLVMRecompileAndRelinkFunction(this.engine, fn.value());
	}

	public LLVMTargetDataRef getExecutionEngineTargetData() {
		return LLVMGetExecutionEngineTargetData(this.engine);
	}

	public void addTargetData(PassManager manager) {
		LLVMAddTargetData(this.getExecutionEngineTargetData(),
				manager.manager());
	}

	public void addGlobalMapping(Value global, Pointer<?> addr) {
		LLVMAddGlobalMapping(this.engine, global.value(), addr);
	}

	public Pointer<?> getPointerToGlobal(Value global) {
		return LLVMGetPointerToGlobal(this.engine, global.value());
	}

	static Pointer<LLVMGenericValueRef> internalize(GenericValue[] values) {
		int n = values.length;
		LLVMGenericValueRef[] inner = new LLVMGenericValueRef[n];
		for (int i = 0; i < n; i++) {
			inner[i] = values[i].ref();
		}

		Pointer<LLVMGenericValueRef> array = Pointer.allocateTypedPointers(
				LLVMGenericValueRef.class, n);
		if (array == null) {
			return null;
		}
		array.setArray(inner);

		return array;
	}

}
