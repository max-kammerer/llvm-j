package org.llvm;

import static org.llvm.binding.LLVMLibrary.LLVMBasicBlockAsValue;
import static org.llvm.binding.LLVMLibrary.LLVMDeleteBasicBlock;
import static org.llvm.binding.LLVMLibrary.LLVMGetBasicBlockParent;
import static org.llvm.binding.LLVMLibrary.LLVMGetFirstInstruction;
import static org.llvm.binding.LLVMLibrary.LLVMGetLastInstruction;
import static org.llvm.binding.LLVMLibrary.LLVMGetNextBasicBlock;
import static org.llvm.binding.LLVMLibrary.LLVMGetPreviousBasicBlock;
import static org.llvm.binding.LLVMLibrary.LLVMInsertBasicBlock;
import static org.llvm.binding.LLVMLibrary.LLVMInsertBasicBlockInContext;
import static org.llvm.binding.LLVMLibrary.LLVMMoveBasicBlockAfter;
import static org.llvm.binding.LLVMLibrary.LLVMMoveBasicBlockBefore;

import org.bridj.Pointer;
import org.llvm.binding.LLVMLibrary.LLVMBasicBlockRef;

/**
 * This represents a single basic block in LLVM. A basic block is simply a
 * container of instructions that execute sequentially.
 */
public class BasicBlock {
	private final LLVMBasicBlockRef bb;

	public LLVMBasicBlockRef bb() {
		return this.bb;
	}

	public BasicBlock(LLVMBasicBlockRef bb) {
		this.bb = bb;
	}

	/**
	 * Convert a basic block instance to a value type.
	 */
	public Value asValue() {
		return new Value(LLVMBasicBlockAsValue(this.bb));
	}

	/**
	 * Obtain the function to which a basic block belongs.
	 */
	public Value getParent() {
		return new Value(LLVMGetBasicBlockParent(this.bb));
	}

	/**
	 * Advance a basic block iterator.
	 */
	public BasicBlock getNext() {
		return new BasicBlock(LLVMGetNextBasicBlock(this.bb));
	}

	/**
	 * Go backwards in a basic block iterator.
	 */
	public BasicBlock getPrevious() {
		return new BasicBlock(LLVMGetPreviousBasicBlock(this.bb));
	}

	/**
	 * Insert a new basic block before this basic block, and return it.
	 */
	public BasicBlock insert(String name) {
		return new BasicBlock(LLVMInsertBasicBlock(this.bb,
				Pointer.pointerToCString(name)));
	}

	/**
	 * Insert a new basic block before this basic block, and return it
	 */
	public BasicBlock insertInContext(Context c, String name) {
		return new BasicBlock(LLVMInsertBasicBlockInContext(c.context(),
				this.bb, Pointer.pointerToCString(name)));
	}

	/**
	 * Remove a basic block from a function and delete it.<br>
	 * This deletes the basic block from its containing function and deletes<br>
	 * the basic block itself.
	 */
	public void delete() {
		LLVMDeleteBasicBlock(this.bb);
	}

	/**
	 * Move a basic block to before another one.
	 */
	public void moveBefore(BasicBlock movePos) {
		LLVMMoveBasicBlockBefore(this.bb, movePos.bb());
	}

	/**
	 * Move a basic block to after another one.
	 */
	public void moveAfter(BasicBlock movePos) {
		LLVMMoveBasicBlockAfter(this.bb, movePos.bb());
	}

	/**
	 * Obtain the first instruction in a basic block.<br>
	 * The returned LLVMValueRef corresponds to a llvm::Instruction<br>
	 * instance.
	 */
	public Value getFirstInstruction() {
		return new Value(LLVMGetFirstInstruction(this.bb));
	}

	/**
	 * Obtain the last instruction in a basic block.<br>
	 * The returned LLVMValueRef corresponds to a LLVM:Instruction.
	 */
	public Value getLastInstruction() {
		return new Value(LLVMGetLastInstruction(this.bb));
	}

}
