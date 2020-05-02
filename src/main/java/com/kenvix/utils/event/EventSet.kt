//--------------------------------------------------
// Class EventSet
//--------------------------------------------------
// Written by Kenvix <i@kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.event

open class EventSet<E> : EventContainer<E> {
    private val set: HashSet<EventHandler<E>> = HashSet()

    override fun plusAssign(handler: EventHandler<E>) {
        set.add(handler)
    }

    override fun minusAssign(handler: EventHandler<E>) {
        set.remove(handler)
    }

    override fun invoke(data: E) {
        set.forEach { it(data) }
    }

    override fun contains(handler: EventHandler<E>): Boolean = set.contains(handler)
    override fun clear() = set.clear()
    override fun count(): Int = set.count()
}