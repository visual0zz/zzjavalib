package com.zz.utils.threadsafe.storage.exceptions;

public class InvalidDatabaseKeyException extends RuntimeException {
    public InvalidDatabaseKeyException() {
        super("错误的键格式。");
    }

    public InvalidDatabaseKeyException(String message) {
        super(message);
    }

    public InvalidDatabaseKeyException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidDatabaseKeyException(Throwable cause) {
        super(cause);
    }
}
