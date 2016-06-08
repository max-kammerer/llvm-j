package org.llvm.test

import org.llvm.Builder
import org.llvm.Module
import org.llvm.TypeRef
import org.llvm.Value

class Variable(val name: String, val slot: Value) {

    fun load(builder: Builder): Value {
        return builder.buildLoad(slot, name)
    }

    fun store(builder: Builder, value: Value): Value {
        return builder.buildStore(value, slot)
    }
}

fun Builder.variable(name: String, type: TypeRef): Variable {
    val slot = buildAlloca(type, name)
    return Variable(name, slot)
}

fun Builder.load(variable: Variable): Value {
    return variable.load(this)
}

fun Builder.store(value: Value, variable: Variable) {
    variable.store(this, value)
}

inline fun Module.addFunction(name: String, functionType: TypeRef, init: Value.() -> Unit): Value {
    val function = addFunction(name, functionType)
    function.init()
    return function
}

inline fun Value.param(index: Int, update: Value.() -> Unit) {
    getParam(index).update()
}

fun Value.param(index: Int): Value {
    return getParam(index)
}

inline fun Builder.build(operations: Builder.() -> Unit) {
    this.operations()
}

