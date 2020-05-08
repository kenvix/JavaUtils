package com.kenvix.utils.exception;

public class UnknownServerException extends RequestException {
    int code;

    public UnknownServerException(int code) {
        this.code = code;
    }

    public UnknownServerException(String message, int code) {
        super(message);
        this.code = code;
    }

    public UnknownServerException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public UnknownServerException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public UnknownServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public UnknownServerException() {
    }

    public UnknownServerException(String message) {
        super(message);
    }

    public UnknownServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownServerException(Throwable cause) {
        super(cause);
    }

    public UnknownServerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
