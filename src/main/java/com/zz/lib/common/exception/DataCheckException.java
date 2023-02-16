package com.zz.lib.common.exception;


public class DataCheckException extends RuntimeException {
    public DataCheckException() {
        super();
    }

    public DataCheckException(String message) {
        super(message);
    }

    public DataCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataCheckException(Throwable cause) {
        super(cause);
    }
}
