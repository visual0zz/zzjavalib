package com.zz.lib.common.exception;

public class DataContentException extends DataCheckException {
    public DataContentException() {
        super();
    }

    public DataContentException(String message) {
        super(message);
    }

    public DataContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataContentException(Throwable cause) {
        super(cause);
    }

    protected DataContentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
