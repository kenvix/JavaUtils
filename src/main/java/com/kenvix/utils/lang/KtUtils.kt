@file:JvmName("KtUtils")

package com.kenvix.utils.lang

@Suppress("NOTHING_TO_INLINE")
inline fun Any?.toUnit() = Unit

val <T> T.weakReference
    get() = WeakRef(this)