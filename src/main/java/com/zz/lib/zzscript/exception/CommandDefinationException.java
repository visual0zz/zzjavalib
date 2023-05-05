package com.zz.lib.zzscript.exception;

public class CommandDefinationException extends RuntimeException{
    public CommandDefinationException() {
        super();
    }

    public CommandDefinationException(String message) {
        super(message);
    }

    public CommandDefinationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandDefinationException(Throwable cause) {
        super(cause);
    }

    protected CommandDefinationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
