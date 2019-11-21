package com.zz.utils.threadunsafe.math.exception;

public class InvalidMatrixOperationException extends RuntimeException{
    public InvalidMatrixOperationException() {
        super();
    }

    public InvalidMatrixOperationException(String message) {
        super(message);
    }

    public InvalidMatrixOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMatrixOperationException(Throwable cause) {
        super(cause);
    }

    protected InvalidMatrixOperationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
