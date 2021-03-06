//--------------------------------------------------
// Class AutoWeakReference
//--------------------------------------------------
// Written by Kenvix <i@com.kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.lang

import java.lang.ref.WeakReference
import java.util.concurrent.CancellationException

@Suppress("MemberVisibilityCanBePrivate")
class WeakRef<T> internal constructor(obj: T) {
    val ref = WeakReference(obj)

    operator fun invoke(): T {
        return ref.get() ?: throw CancellationException("Object already collected")
    }
}