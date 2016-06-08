package org.llvm.test

import junit.framework.TestCase
import org.llvm.*

abstract class ExecutionTest : TestCase() {

    fun execute(module: Module, function: Value, vararg params: GenericValue): GenericValue {
        return execute(module, function, params) {}
    }

    fun execute(module: Module, function: Value, params: Array<out GenericValue>, passes: PassManager.() -> Unit = {}): GenericValue {
        module.dumpModule()
        module.verify()

        //optimization passes
        val pass = PassManager.create()
        pass.passes()

        // Create an execution engine.
        val ee = ExecutionEngine.createForModule(module)
        // Compile and run!
        return ee.runFunction(function, *params)
    }
}
