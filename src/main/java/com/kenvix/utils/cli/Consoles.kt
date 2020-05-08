@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.kenvix.utils.cli

import com.kenvix.utils.event.eventSetOf

object Consoles {
    val consoleInputHandlers = eventSetOf<String>()

    fun beginReadSystemConsoleSync() {
        while (!Thread.interrupted()) {
            val input = readLine()

            if (input?.isNotBlank() == true) {
                consoleInputHandlers.invoke(input)
            }
        }
    }
}