package com.kenvix.utils.event

typealias EventHandler<T> = (T) -> Unit

fun <E> eventSetOf() = EventSet<E>()
fun <E> eventListOf(initSize: Int = 10) = EventList<E>(initSize)