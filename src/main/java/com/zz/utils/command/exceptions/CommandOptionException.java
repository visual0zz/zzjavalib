package com.zz.utils.command.exceptions;

public class CommandOptionException extends Exception{

    public CommandOptionException() {
        super();
    }

    public CommandOptionException(String message) {
        super(message);
    }

    public CommandOptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandOptionException(Throwable cause) {
        super(cause);
    }

    protected CommandOptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
