package com.kenvix.utils.exception;

public class ForbiddenOperationException extends RequestException {
    public ForbiddenOperationException() {
    }

    public ForbiddenOperationException(String message) {
        super(message);
    }

    public ForbiddenOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenOperationException(Throwable cause) {
        super(cause);
    }

    public ForbiddenOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
