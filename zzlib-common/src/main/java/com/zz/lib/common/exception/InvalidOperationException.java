package com.zz.lib.common.exception;

/**
 * 非法操作错误，试图执行一个不存在的指令或者调用一个不存在的函数
 */
public class InvalidOperationException extends DataCheckException{
    public InvalidOperationException() {
        super();
    }

    public InvalidOperationException(String message) {
        super(message);
    }

    public InvalidOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOperationException(Throwable cause) {
        super(cause);
    }

}
