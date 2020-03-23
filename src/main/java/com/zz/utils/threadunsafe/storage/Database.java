package com.zz.utils.threadunsafe.storage;

import java.io.Closeable;
import java.io.IOException;

public interface Database extends Closeable {
    String get(String key);
    void set(String key,String value);
    @Override
    void close() throws IOException;
}
