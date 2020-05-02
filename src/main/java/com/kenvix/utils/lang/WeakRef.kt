//--------------------------------------------------
// Class AutoWeakReference
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.lang

import java.lang.ref.WeakReference
import java.util.concurrent.CancellationException

class WeakRef<T> internal constructor(obj: T) {
    val ref = WeakReference(obj)

    operator fun invoke(): T {
        return ref.get() ?: throw CancellationException("Object already collected")
    }
}