package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.InvalidDatabaseKeyException;

import java.io.File;
import java.util.regex.Pattern;

/**
 * 数据库key到路径的翻译工具类
 */
class DatabaseKeyTool {
    /**
     * 数据库的key 应该是 a.b.c的形式，汉字大小写字母数字和下划线是允许的，其他的不允许
     * @param key key字符串
     * @return true表示key符合格式 false表示不符合格式
     */
    private static final Pattern positive=Pattern.compile(
            "([\\u4e00-\\u9fa5a-zA-z0-9_]+\\.)*[\\u4e00-\\u9fa5a-zA-z0-9_]+");//需要匹配的模式
    private static final Pattern negative=Pattern.compile(
            "[\\[\\]^$%#@!~`&*()\\-=+\\\\|/\";:'{}]");//需要排除的奇怪字符
    public static boolean isKeyValid(String key){
        key=key.trim();
        if(!positive.matcher(key).matches())return false;
        if(negative.matcher(key).find())return false;
        return true;
    }

    /**
     * 将key翻译为路径
     * @param key 指定读写位置的数据库key
     * @param baseFolder 数据库的根目录
     * @return 对应key的储存文件路径
     */
    public static File keyToFilePath(String key,File baseFolder){
        if (!isKeyValid(key))throw new InvalidDatabaseKeyException("bad key: <"+key+"> ");
        return new File(baseFolder.getAbsolutePath()
                +File.separator+
                key.replace(".", "@"+File.separator)+"$");
    }
}
