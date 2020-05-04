//--------------------------------------------------
// Class CommonBusinessException
//--------------------------------------------------
// Written by Kenvix <i@com.kenvix.com>
//--------------------------------------------------

package com.kenvix.utils.exception;

public class CommonBusinessException extends RequestException {
    private int code;

    public int getCode() {
        return code;
    }

    public CommonBusinessException setCode(int code) {
        this.code = code;
        return this;
    }

    public CommonBusinessException(int code) {
        this.code = code;
    }

    public CommonBusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    public CommonBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommonBusinessException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public CommonBusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
