package com.zz.lib.common.exception;

/**
 * 输入尺寸校验错误，比如理论上应该输入一个长度为3的列表世纪输入了长度为4的列表
 */
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
