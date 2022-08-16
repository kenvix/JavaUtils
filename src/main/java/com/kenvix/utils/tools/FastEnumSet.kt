//--------------------------------------------------
// Class FastEnumSet
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.tools

import java.util.function.Consumer

@Suppress("DeprecatedCallableAddReplaceWith")
@JvmInline
value class FastEnumSet32<E: Enum<E>> private constructor(private val data: UInt = 0u): Set<Enum<E>> {
    override val size: Int
        get() = data.countOneBits()

    fun addAllOf(elements: Collection<Enum<E>>): FastEnumSet32<E> {
        var newData = data
        for (element in elements) {
            checkOrdinal(element)
            newData = newData or (1u shl element.ordinal)
        }
        return FastEnumSet32(newData)
    }

    fun addOf(element: Enum<E>): FastEnumSet32<E> {
        checkOrdinal(element)
        val newData = data or (1u shl element.ordinal)
        return FastEnumSet32(newData)
    }

    fun removeOf(element: Enum<E>): FastEnumSet32<E> {
        checkOrdinal(element)
        val newData = data and (1u shl element.ordinal).inv()
        return FastEnumSet32(newData)
    }

    fun removeAllOf(elements: Collection<Enum<E>>): FastEnumSet32<E> {
        var newData = data
        for (element in elements) {
            checkOrdinal(element)
            newData = newData and (1u shl element.ordinal).inv()
        }
        return FastEnumSet32(newData)
    }

    override fun isEmpty(): Boolean = size == 0

    @Deprecated("Traversing is NOT supported", level = DeprecationLevel.ERROR)
    override fun iterator(): Iterator<Enum<E>> {
        throw NotImplementedError()
    }

    @Deprecated("Traversing is NOT supported", level = DeprecationLevel.ERROR)
    override fun forEach(action: Consumer<in Enum<E>>?) {
        throw NotImplementedError()
    }

    override fun containsAll(elements: Collection<Enum<E>>): Boolean {
        elements.forEach { if (!contains(it)) return false }
        return true
    }

    override operator fun contains(element: Enum<E>): Boolean {
        element.ordinal.toUInt().and(data).let {
            return it != 0u
        }
    }

    companion object {
        const val MAX_ORDINAL = 32

        @JvmStatic
        fun <E: Enum<E>> of(): FastEnumSet32<E> = FastEnumSet32()

        @JvmStatic
        private fun <E: Enum<E>> checkOrdinal(element: Enum<E>) {
            if (element.ordinal > MAX_ORDINAL)
                throw IndexOutOfBoundsException("Ordinal out of bounds. Max supported ordinal is $MAX_ORDINAL")
        }

        @JvmStatic
        fun <E: Enum<E>> of(element: Enum<E>): FastEnumSet32<E> {
            checkOrdinal(element)
            return FastEnumSet32(1u shl element.ordinal)
        }

        @JvmStatic
        fun <E: Enum<E>> of(element1: Enum<E>, element2: Enum<E>): FastEnumSet32<E> {
            checkOrdinal(element1)
            checkOrdinal(element2)
            return FastEnumSet32((1u shl element1.ordinal) or (1u shl element2.ordinal))
        }

        @JvmStatic
        fun <E: Enum<E>> of(element1: Enum<E>, element2: Enum<E>, element3: Enum<E>): FastEnumSet32<E> {
            checkOrdinal(element1)
            checkOrdinal(element2)
            checkOrdinal(element3)
            return FastEnumSet32((1u shl element1.ordinal) or (1u shl element2.ordinal) or (1u shl element3.ordinal))
        }

        @JvmStatic
        fun <E: Enum<E>> of(element1: Enum<E>, element2: Enum<E>, element3: Enum<E>, element4: Enum<E>): FastEnumSet32<E> {
            checkOrdinal(element1)
            checkOrdinal(element2)
            checkOrdinal(element3)
            checkOrdinal(element4)
            return FastEnumSet32((1u shl element1.ordinal) or (1u shl element2.ordinal) or (1u shl element3.ordinal) or (1u shl element4.ordinal))
        }

        @JvmStatic
        fun <E: Enum<E>> of(vararg elements: Enum<E>): FastEnumSet32<E> {
            val emptyOf = FastEnumSet32<E>()
            return emptyOf.addAllOf(elements.asList())
        }
    }
}