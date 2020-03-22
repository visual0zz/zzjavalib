package com.zz.utils.threadunsafe.storage.impl;

import com.zz.utils.threadunsafe.storage.InvalidDatabaseKeyException;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.regex.Pattern;

class DatabaseKeyTool {
    /**
     * 数据库的key 应该是 a.b.c的形式，汉字大小写字母数字和下划线是允许的，其他的不允许
     * @param key key字符串
     * @return true表示key符合格式 false表示不符合格式
     */
    public static boolean isKeyValid(String key){
        Pattern p=Pattern.compile("([\\u4e00-\\u9fa5a-zA-z0-9_]+\\.)*[\\u4e00-\\u9fa5a-zA-z0-9_]+");
        return p.matcher(key.trim()).matches();
    }
    public static File keyToFilePath(String key,File baseFolder){
        if (!isKeyValid(key))throw new InvalidDatabaseKeyException("bad key: <"+key+"> ");
        return new File(baseFolder.getAbsolutePath()
                +File.separator+
                key.replace(".", File.separator));
    }
}
