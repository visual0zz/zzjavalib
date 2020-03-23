package com.zz.utils.threadsafe.storage;

public class DatabaseIOException extends RuntimeException {
    public DatabaseIOException() {
        super();
    }

    public DatabaseIOException(String message) {
        super(message);
    }

    public DatabaseIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DatabaseIOException(Throwable cause) {
        super(cause);
    }
}
