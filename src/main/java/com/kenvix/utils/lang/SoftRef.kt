//--------------------------------------------------
// Class AutoWeakReference
//--------------------------------------------------
// Written by Kenvix <i@com.kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.lang

import java.lang.ref.SoftReference
import java.util.concurrent.CancellationException

@Suppress("MemberVisibilityCanBePrivate")
class SoftRef<T> internal constructor(obj: T) {
    val ref = SoftReference(obj)

    operator fun invoke(): T {
        return ref.get() ?: throw CancellationException("Object already collected")
    }
}