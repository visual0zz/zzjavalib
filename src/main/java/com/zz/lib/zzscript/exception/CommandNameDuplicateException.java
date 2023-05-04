package com.zz.lib.zzscript.exception;

public class CommandNameDuplicateException extends RuntimeException{
    public CommandNameDuplicateException() {
        super();
    }

    public CommandNameDuplicateException(String message) {
        super(message);
    }

    public CommandNameDuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandNameDuplicateException(Throwable cause) {
        super(cause);
    }

    protected CommandNameDuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
