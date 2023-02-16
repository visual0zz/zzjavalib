package com.zz.lib.common.exception;

public class DataContentException extends DataCheckException {
    public DataContentException() {
        super();
    }

    public DataContentException(String message) {
        super(message);
    }

    public DataContentException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataContentException(Throwable cause) {
        super(cause);
    }

}
