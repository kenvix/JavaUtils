@file:JvmName("KtUtils")
@file:Suppress("unused")

package com.kenvix.utils.lang

import com.kenvix.utils.annotation.Description
import java.io.*
import kotlin.reflect.full.findAnnotation

val <T> T.weakReference
    get() = WeakRef(this)

val <T> T.softReference
    get() = SoftRef(this)

val Any?.descriptionAnnotationValue: String?
    get() = if (this == null) null else this::class.findAnnotation<Description>()?.message

@Suppress("NOTHING_TO_INLINE")
inline fun Any?.toUnit() = Unit

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