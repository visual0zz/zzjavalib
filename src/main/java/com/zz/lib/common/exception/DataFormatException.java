package com.zz.lib.common.exception;

public class DataFormatException extends DataCheckException {
    public DataFormatException() {
        super();
    }

    public DataFormatException(String message) {
        super(message);
    }

    public DataFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataFormatException(Throwable cause) {
        super(cause);
    }

    protected DataFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
