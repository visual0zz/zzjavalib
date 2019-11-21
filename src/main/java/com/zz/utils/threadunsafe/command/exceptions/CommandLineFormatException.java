package com.zz.utils.threadunsafe.command.exceptions;

public class CommandLineFormatException extends Exception {
    public CommandLineFormatException() {
        super();
    }

    public CommandLineFormatException(String message) {
        super(message);
    }

    public CommandLineFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandLineFormatException(Throwable cause) {
        super(cause);
    }

    protected CommandLineFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
