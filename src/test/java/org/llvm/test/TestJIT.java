package org.llvm.test;

import org.llvm.*;

/**
 * http://www.mdevan.org/llvm-py/examples.html
 */
public class TestJIT extends ExecutionTest {

	public void testSimpleFn() {
		Module mod = Module.createWithName("test_module");
		TypeRef ty_i32 = TypeRef.intType(32);
		TypeRef ty_func = TypeRef.functionType(ty_i32, ty_i32, ty_i32);

		// Add a new function to the module, named "sum"
		Value f_sum = mod.addFunction("sum", ty_func);

		// Name the function's params
		f_sum.getParam(0).setValueName("a");
		f_sum.getParam(1).setValueName("b");

		// Create a basic block named "entry"
		BasicBlock bb = f_sum.appendBasicBlock("entry");

		// Create an instruction builder, and position it.
		Builder builder = Builder.createBuilder();
		builder.positionBuilderAtEnd(bb);

		// Build some instructions
		Value tmp = builder.buildAdd(f_sum.getParam(0), f_sum.getParam(1),
				"tmp");
		builder.buildRet(tmp);

		// The arguments need to be passed as "GenericValue" objects.
		boolean SIGNED = true;
		GenericValue arg1 = GenericValue.createInt(ty_i32, 2, SIGNED);
		GenericValue arg2 = GenericValue.createInt(ty_i32, 40, SIGNED);

		// Compile and run!
		GenericValue retval = execute(mod, f_sum, arg1, arg2);

		long retvalVal = retval.toInt(SIGNED);

		// The return value is also GenericValue.
		assertEquals(42, retvalVal);
	}

}
