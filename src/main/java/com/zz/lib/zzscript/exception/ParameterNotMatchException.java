package com.zz.lib.zzscript.exception;

public class ParameterNotMatchException extends RuntimeException{
    public ParameterNotMatchException() {
        super();
    }

    public ParameterNotMatchException(String message) {
        super(message);
    }

    public ParameterNotMatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParameterNotMatchException(Throwable cause) {
        super(cause);
    }

    protected ParameterNotMatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
