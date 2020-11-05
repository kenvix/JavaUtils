//--------------------------------------------------
// Class CallerClass
//--------------------------------------------------
// Written by Kenvix <i@com.kenvix.com>
//--------------------------------------------------
package com.kenvix.utils.lang

@Suppress("unused")
object CallerClass : SecurityManager() {
    fun get(): Array<Class<*>> {
        return classContext
    }

    operator fun get(depth: Int): Class<*> {
        return classContext[depth]
    }

    public override fun getClassContext(): Array<Class<*>> = super.getClassContext()
}