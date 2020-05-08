package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.impl.interfaces.KeyValueDatabase;

/**
 * 密码生成用的数据库，不储存任何值，用于生成一定格式的字符串
 *
 */
public class PasswordGenerateKVDB implements KeyValueDatabase {

    public static PasswordGenerateKVDB getInstance(String seed){return new PasswordGenerateKVDB(seed);}
    private PasswordGenerateKVDB(String seed){}
    @Override
    public String get(String key) {
        return null;
    }

    @Override public void set(String key,String value){}
}
