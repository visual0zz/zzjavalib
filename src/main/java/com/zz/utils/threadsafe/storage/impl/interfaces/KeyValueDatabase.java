package com.zz.utils.threadsafe.storage.impl.interfaces;

public interface KeyValueDatabase {
    String get(String key);
    void set(String key,String value);
}
