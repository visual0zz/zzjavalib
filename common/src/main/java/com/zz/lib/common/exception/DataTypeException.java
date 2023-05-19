package com.zz.lib.common.exception;

/**
 * 数据类型校验错误，比如需要一个字符串，输入了一个整数数组
 */
public class DataTypeException extends DataCheckException{
    public DataTypeException() {
        super();
    }

    public DataTypeException(String message) {
        super(message);
    }

    public DataTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataTypeException(Throwable cause) {
        super(cause);
    }

}
