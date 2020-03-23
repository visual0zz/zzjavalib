package com.zz.utils.threadsafe.storage;

public class ClosedDatabaseException extends RuntimeException {
    public ClosedDatabaseException() {
        super();
    }

    public ClosedDatabaseException(String message) {
        super(message);
    }

    public ClosedDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClosedDatabaseException(Throwable cause) {
        super(cause);
    }
}
