package org.llvm;

import static org.llvm.binding.LLVMLibrary.LLVMAddAggressiveDCEPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddArgumentPromotionPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddCFGSimplificationPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddConstantMergePass;
import static org.llvm.binding.LLVMLibrary.LLVMAddConstantPropagationPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddDeadArgEliminationPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddDeadStoreEliminationPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddDemoteMemoryToRegisterPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddFunctionAttrsPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddFunctionInliningPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddGVNPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddGlobalDCEPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddGlobalOptimizerPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddIPConstantPropagationPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddIPSCCPPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddIndVarSimplifyPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddInstructionCombiningPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddInternalizePass;
import static org.llvm.binding.LLVMLibrary.LLVMAddJumpThreadingPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddLICMPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddLoopDeletionPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddLoopRotatePass;
import static org.llvm.binding.LLVMLibrary.LLVMAddLoopUnrollPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddLoopUnswitchPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddMemCpyOptPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddPromoteMemoryToRegisterPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddPruneEHPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddReassociatePass;
import static org.llvm.binding.LLVMLibrary.LLVMAddSCCPPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddScalarReplAggregatesPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddScalarReplAggregatesPassWithThreshold;
import static org.llvm.binding.LLVMLibrary.LLVMAddSimplifyLibCallsPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddStripDeadPrototypesPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddStripSymbolsPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddTailCallEliminationPass;
import static org.llvm.binding.LLVMLibrary.LLVMAddVerifierPass;
import static org.llvm.binding.LLVMLibrary.LLVMCreateFunctionPassManager;
import static org.llvm.binding.LLVMLibrary.LLVMCreateFunctionPassManagerForModule;
import static org.llvm.binding.LLVMLibrary.LLVMCreatePassManager;
import static org.llvm.binding.LLVMLibrary.LLVMDisposePassManager;
import static org.llvm.binding.LLVMLibrary.LLVMFinalizeFunctionPassManager;
import static org.llvm.binding.LLVMLibrary.LLVMInitializeFunctionPassManager;
import static org.llvm.binding.LLVMLibrary.LLVMRunFunctionPassManager;
import static org.llvm.binding.LLVMLibrary.LLVMRunPassManager;

import org.llvm.binding.LLVMLibrary.LLVMModuleProviderRef;
import org.llvm.binding.LLVMLibrary.LLVMPassManagerRef;

public class PassManager {

	private LLVMPassManagerRef manager;

	LLVMPassManagerRef manager() {
		return this.manager;
	}

	PassManager(LLVMPassManagerRef manager) {
		this.manager = manager;
	}

	/**
	 * Constructs a new whole-module pass pipeline. This type of pipeline is<br>
	 * suitable for link-time optimization and whole-module transformations.
	 */
	public static PassManager create() {
		return new PassManager(LLVMCreatePassManager());
	}

	/**
	 * Constructs a new function-by-function pass pipeline over the module<br>
	 * provider. It does not take ownership of the module provider. This type of<br>
	 * pipeline is suitable for code generation and JIT compilation tasks.
	 */
	public static PassManager createForModule(Module m) {
		return new PassManager(
				LLVMCreateFunctionPassManagerForModule(m.module()));
	}

	/**
	 * Deprecated: Use LLVMCreateFunctionPassManagerForModule instead.
	 */
	public static PassManager createFPM(LLVMModuleProviderRef mp) {
		return new PassManager(LLVMCreateFunctionPassManager(mp));
	}

	@Override
	public void finalize() {
		this.dispose();
	}

	/**
	 * Finalizes all of the function passes scheduled in in the function pass<br>
	 * manager. Returns 1 if any of the passes modified the module, 0 otherwise.<br>
	 * Frees the memory of a pass pipeline. For function pipelines, does not
	 * free<br>
	 * the module provider.
	 */
	public boolean dispose() {
		boolean res = LLVMFinalizeFunctionPassManager(this.manager) == 1;
		LLVMDisposePassManager(this.manager);
		this.manager = null;
		return res;
	}

	/* PassManager */
	// public static native int LLVMRunPassManager(LLVMPassManagerRef pm, LLVMModuleRef m);

	/**
	 * Initializes all of the function passes scheduled in the function pass<br>
	 * manager. Returns true if any of the passes modified the module, false otherwise.
	 */
	public boolean initialize() {
		return LLVMInitializeFunctionPassManager(this.manager) == 1;
	}

	/**
	 * Initializes, executes on the provided module, and finalizes all of the<br>
	 * passes scheduled in the pass manager. Returns true if any of the passes<br>
	 * modified the module, false otherwise.
	 */
	public boolean runForModule(Module m) {
		return LLVMRunPassManager(this.manager, m.module()) == 1;
	}

	/**
	 * Executes all of the function passes scheduled in the function pass
	 * manager<br>
	 * on the provided function. Returns true if any of the passes modified the<br>
	 * function, false otherwise.
	 */
	public boolean runForFunction(Value f) {
		return LLVMRunFunctionPassManager(this.manager, f.value()) == 1;
	}

	/* Function Pass Manager */
	public void addArgumentPromotionPass() {
		LLVMAddArgumentPromotionPass(this.manager);
	}

	public void addConstantMergePass() {
		LLVMAddConstantMergePass(this.manager);
	}

	public void addDeadArgEliminationPass() {
		LLVMAddDeadArgEliminationPass(this.manager);
	}

	public void addFunctionAttrsPass() {
		LLVMAddFunctionAttrsPass(this.manager);
	}

	public void addFunctionInliningPass() {
		LLVMAddFunctionInliningPass(this.manager);
	}

	public void addGlobalDCEPass() {
		LLVMAddGlobalDCEPass(this.manager);
	}

	public void addGlobalOptimizerPass() {
		LLVMAddGlobalOptimizerPass(this.manager);
	}

	public void addIPConstantPropagationPass() {
		LLVMAddIPConstantPropagationPass(this.manager);
	}

	public void addPruneEHPass() {
		LLVMAddPruneEHPass(this.manager);
	}

	public void addIPSCCPPass() {
		LLVMAddIPSCCPPass(this.manager);
	}

	public void addInternalizePass(boolean allButMain) {
		LLVMAddInternalizePass(this.manager, allButMain ? 1 : 0);
	}

	public void addStripDeadPrototypesPass() {
		LLVMAddStripDeadPrototypesPass(this.manager);
	}

	public void addStripSymbolsPass() {
		LLVMAddStripSymbolsPass(this.manager);
	}

	public void addAggressiveDCEPass() {
		LLVMAddAggressiveDCEPass(this.manager);
	}

	public void addCFGSimplificationPass() {
		LLVMAddCFGSimplificationPass(this.manager);
	}

	public void addDeadStoreEliminationPass() {
		LLVMAddDeadStoreEliminationPass(this.manager);
	}

	public void addGVNPass() {
		LLVMAddGVNPass(this.manager);
	}

	public void addIndVarSimplifyPass() {
		LLVMAddIndVarSimplifyPass(this.manager);
	}

	public void addInstructionCombiningPass() {
		LLVMAddInstructionCombiningPass(this.manager);
	}

	public void addJumpThreadingPass() {
		LLVMAddJumpThreadingPass(this.manager);
	}

	public void addLICMPass() {
		LLVMAddLICMPass(this.manager);
	}

	public void addLoopDeletionPass() {
		LLVMAddLoopDeletionPass(this.manager);
	}

	public void addLoopRotatePass() {
		LLVMAddLoopRotatePass(this.manager);
	}

	public void addLoopUnrollPass() {
		LLVMAddLoopUnrollPass(this.manager);
	}

	public void addLoopUnswitchPass() {
		LLVMAddLoopUnswitchPass(this.manager);
	}

	public void addMemCpyOptPass() {
		LLVMAddMemCpyOptPass(this.manager);
	}

	public void addPromoteMemoryToRegisterPass() {
		LLVMAddPromoteMemoryToRegisterPass(this.manager);
	}

	public void addReassociatePass() {
		LLVMAddReassociatePass(this.manager);
	}

	public void addSCCPPass() {
		LLVMAddSCCPPass(this.manager);
	}

	public void addScalarReplAggregatesPass() {
		LLVMAddScalarReplAggregatesPass(this.manager);
	}

	public void addScalarReplAggregatesPassWithThreshold(int threshold) {
		LLVMAddScalarReplAggregatesPassWithThreshold(this.manager, threshold);
	}

	public void addSimplifyLibCallsPass() {
		LLVMAddSimplifyLibCallsPass(this.manager);
	}

	public void addTailCallEliminationPass() {
		LLVMAddTailCallEliminationPass(this.manager);
	}

	public void addConstantPropagationPass() {
		LLVMAddConstantPropagationPass(this.manager);
	}

	public void addDemoteMemoryToRegisterPass() {
		LLVMAddDemoteMemoryToRegisterPass(this.manager);
	}

	public void addVerifierPass() {
		LLVMAddVerifierPass(this.manager);
	}

}