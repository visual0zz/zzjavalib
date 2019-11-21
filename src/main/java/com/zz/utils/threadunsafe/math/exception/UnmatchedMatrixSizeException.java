package com.zz.utils.threadunsafe.math.exception;

/**
 * 表示发生了两个尺寸不匹配的矩阵的运算
 */
public class UnmatchedMatrixSizeException extends RuntimeException{
    public UnmatchedMatrixSizeException() {
        super();
    }

    public UnmatchedMatrixSizeException(String message) {
        super(message);
    }

    public UnmatchedMatrixSizeException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnmatchedMatrixSizeException(Throwable cause) {
        super(cause);
    }

    protected UnmatchedMatrixSizeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
