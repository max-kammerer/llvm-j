package org.llvm.test

import junit.framework.Assert
import org.llvm.*

class SlotVariableTest : ExecutionTest() {

    fun testSimpleVarTest() {
        val mod = Module.createWithName("test_module")
        val ty_i32 = TypeRef.intType(32)

        val f_sum: Value = mod.addFunction("sumAndInc", TypeRef.functionType(ty_i32, ty_i32, ty_i32)) {
            // Name the function's params
            param(0) { valueName = "a" }
            param(1) { valueName = "b" }

            val bb = appendBasicBlock("entry")

            // Create an instruction builder, and position it.
            val builder = Builder.createBuilder()
            builder.build {
                positionBuilderAtEnd(bb)

                val tmpVar = variable("tmp", ty_i32)
                store(buildAdd(param(0), param(1), "tmp"), tmpVar)
                val inc = buildAdd(load(tmpVar), ty_i32.constInt(1, true), "inc")
                store(inc, tmpVar)
                buildRet(load(tmpVar))
            }
        }

        val SIGNED = true
        val retvalVal = execute(mod, f_sum, GenericValue.createInt(ty_i32, 2, SIGNED), GenericValue.createInt(ty_i32, 40, SIGNED)).toInt(SIGNED)

        Assert.assertEquals(43, retvalVal)
    }
}
