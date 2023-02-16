package com.zz.lib.common.exception;

public class DataSizeException extends DataCheckException{
    public DataSizeException() {
        super();
    }

    public DataSizeException(String message) {
        super(message);
    }

    public DataSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataSizeException(Throwable cause) {
        super(cause);
    }

}
