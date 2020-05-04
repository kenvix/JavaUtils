package com.kenvix.utils.exception;

public class ServerFaultException extends RequestException {
    int code;

    public ServerFaultException(int code) {
        this.code = code;
    }

    public ServerFaultException(String message, int code) {
        super(message);
        this.code = code;
    }

    public ServerFaultException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public ServerFaultException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public ServerFaultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    public ServerFaultException() {
    }

    public ServerFaultException(String message) {
        super(message);
    }

    public ServerFaultException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerFaultException(Throwable cause) {
        super(cause);
    }

    public ServerFaultException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
