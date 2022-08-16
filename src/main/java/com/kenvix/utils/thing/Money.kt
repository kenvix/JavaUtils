//--------------------------------------------------
// Class Money
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

@file:Suppress("NOTHING_TO_INLINE")

package com.kenvix.utils.thing

typealias IntMoney = Int
fun IntMoney.parseIntMoney(): IntMoney = 1234
fun IntMoney.toString() = "${this / 100}.${this % 100}"

@JvmInline
@Suppress("MemberVisibilityCanBePrivate")
value class LongMoney constructor(val value: Long) {
    constructor(moneyString: String): this(parseMoneyToLong(moneyString))

    override fun toString(): String {
        return "${value / 100}.${value % 100}"
    }

    inline operator fun plus(another: LongMoney): LongMoney = LongMoney(value + another.value)
    inline operator fun minus(another: LongMoney): LongMoney =  LongMoney(value - another.value)
    inline operator fun times(another: LongMoney): LongMoney =  LongMoney(value * another.value)
    inline operator fun div(another: LongMoney): LongMoney =  LongMoney(value / another.value)
    inline operator fun rem(another: LongMoney): LongMoney =  LongMoney(value % another.value)

    inline operator fun unaryMinus(): LongMoney =  LongMoney(-value)
    inline operator fun unaryPlus(): LongMoney =  LongMoney(+value)
    inline operator fun compareTo(another: LongMoney) = value.compareTo(another.value)

    inline fun getPrimaryPart(): Long {
        return value / 100
    }

    inline fun getCentPart(): Byte {
        return (value % 100).toByte()
    }

    companion object Utils {
        @JvmStatic
        fun parseLongMoney(moneyString: String): LongMoney {
            TODO()
        }

        @JvmStatic
        fun parseLongMoneySerializedValue(value: Long): LongMoney = LongMoney(value)

        @JvmStatic
        private fun parseMoneyToLong(moneyString: String): Long {
            TODO()
        }
    }
}