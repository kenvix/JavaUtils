package com.kenvix.utils.log

import java.util.logging.Level
import java.util.logging.Logger

fun Logger.config(throwable: Throwable, extraMessage: String = "") = this.log(Level.CONFIG, extraMessage, throwable)
fun Logger.finest(throwable: Throwable, extraMessage: String = "") = this.log(Level.FINEST, extraMessage, throwable)
fun Logger.finer(throwable: Throwable, extraMessage: String = "") = this.log(Level.FINER, extraMessage, throwable)
fun Logger.fine(throwable: Throwable, extraMessage: String = "") = this.log(Level.FINE, extraMessage, throwable)
fun Logger.info(throwable: Throwable, extraMessage: String = "") = this.log(Level.INFO, extraMessage, throwable)
fun Logger.warning(throwable: Throwable, extraMessage: String = "") = this.log(Level.WARNING, extraMessage, throwable)
fun Logger.severe(throwable: Throwable, extraMessage: String = "") = this.log(Level.SEVERE, extraMessage, throwable)