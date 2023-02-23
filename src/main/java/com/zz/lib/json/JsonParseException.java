package com.zz.lib.json;

/**
 * json字符串解析错误，输入json字符串格式异常导致无法解析。
 */

public class JsonParseException extends RuntimeException {
    public JsonParseException() {
        super();
    }

    public JsonParseException(String message) {
        super(message);
    }

    public JsonParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonParseException(Throwable cause) {
        super(cause);
    }
}
