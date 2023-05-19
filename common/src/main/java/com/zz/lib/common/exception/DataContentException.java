package com.zz.lib.common.exception;

/**
 * 数据内容校验错误，比如预期输入一个手机号字符串，但实际输入的字符串是英文单词
 */
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
