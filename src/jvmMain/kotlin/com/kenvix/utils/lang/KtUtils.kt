@file:JvmName("KtUtils")
@file:Suppress("unused")

package com.kenvix.utils.lang

import java.io.*

val <T> T.weakReference
    get() = WeakRef(this)

@Suppress("NOTHING_TO_INLINE")
actual inline fun Any?.toUnit() = Unit

fun Serializable.serializeToBytes(): ByteArray {
    return ByteArrayOutputStream().use { bytes ->
        ObjectOutputStream(bytes).use { obj ->
            obj.writeObject(this)
        }

        bytes.toByteArray()
    }
}


fun <T> Class<T>.newInstanceFromSerialized(byteArray: ByteArray): T {
    return ByteArrayInputStream(byteArray).use { bytes ->
        ObjectInputStream(bytes).use { obj ->
            val result = obj.readObject()

            if (!this.isInstance(result))
                throw IllegalArgumentException("Input bytes are not supertype of this class")

            @Suppress("UNCHECKED_CAST")
            result as T
        }
    }
}