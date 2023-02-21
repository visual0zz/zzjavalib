package com.zz.lib.common.json;

/**
 * 数据校验错误，输入数据的尺寸、类型、内容模式等不符合预期。
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
