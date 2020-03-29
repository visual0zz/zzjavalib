package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.KeyValueDatabase;

import java.io.IOException;

/**
 * 黑体数据库
 * 一个假数据库，不储存任何数据，永远返回固定值，
 */
public class BlackBodyKVDB implements KeyValueDatabase {
    private final String value= String.valueOf(hashCode());
    @Override
    public String get(String key) {
        return value;
    }

    @Override
    public void set(String key, String value) {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public boolean isClosed() {
        return false;
    }
}
