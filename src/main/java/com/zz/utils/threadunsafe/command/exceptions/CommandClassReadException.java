package com.zz.utils.threadunsafe.command.exceptions;

public class CommandClassReadException extends Exception{
    public CommandClassReadException() {
        super();
    }

    public CommandClassReadException(String message) {
        super(message);
    }

    public CommandClassReadException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandClassReadException(Throwable cause) {
        super(cause);
    }

    protected CommandClassReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
