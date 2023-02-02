package com.zz.lib.common.exception;

public class DataSizeException extends DataCheckException{
    public DataSizeException() {
        super();
    }

    public DataSizeException(String message) {
        super(message);
    }

    public DataSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSizeException(Throwable cause) {
        super(cause);
    }

    protected DataSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
