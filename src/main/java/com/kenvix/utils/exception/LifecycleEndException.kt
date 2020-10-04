package com.kenvix.utils.exception

import java.util.concurrent.CancellationException

class LifecycleEndException : CancellationException {
    constructor() : super("Lifecycle of the activity or service, etc. is end, so this job is cancelled") {}
    constructor(message: String?) : super(message)
}