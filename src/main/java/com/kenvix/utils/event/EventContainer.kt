//--------------------------------------------------
// Class IEvent
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.event

interface EventContainer<E> {
    operator fun plusAssign(handler: EventHandler<E>)
    operator fun minusAssign(handler: EventHandler<E>)

    operator fun invoke(data: E)
    operator fun contains(handler: EventHandler<E>): Boolean
    fun clear()
    fun count(): Int
}