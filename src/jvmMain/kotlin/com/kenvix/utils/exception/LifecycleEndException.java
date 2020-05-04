package com.kenvix.utils.exception;

import java.util.concurrent.CancellationException;

public class LifecycleEndException extends CancellationException {
    public LifecycleEndException() {
        super("Lifecycle of the activity or service, etc. is end, so this job is cancelled");
    }

    public LifecycleEndException(String message) {
        super(message);
    }
}
