package com.kenvix.utils.exception;

public class InvalidResultException extends RequestException {
    public InvalidResultException() {
        super("非法返回结果");
    }

    public InvalidResultException(String message) {
        super(message);
    }

    public InvalidResultException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidResultException(Throwable cause) {
        super(cause);
    }

    public InvalidResultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
