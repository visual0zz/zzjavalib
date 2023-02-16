package com.zz.lib.common.exception;

/**
 * 数据校验错误，输入数据的尺寸、类型、内容模式等不符合预期。
 */

public class DataCheckException extends RuntimeException {
    public DataCheckException() {
        super();
    }

    public DataCheckException(String message) {
        super(message);
    }

    public DataCheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataCheckException(Throwable cause) {
        super(cause);
    }
}
