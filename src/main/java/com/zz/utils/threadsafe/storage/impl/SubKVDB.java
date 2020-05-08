package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.exceptions.InvalidDatabaseKeyException;
import com.zz.utils.threadsafe.storage.impl.interfaces.KeyValueDatabase;
import com.zz.utils.threadsafe.storage.impl.interfaces.KeyValueRegionDatabase;
import com.zz.utils.threadsafe.storage.impl.util.DatabaseKeyTool;
import com.zz.utils.threadsafe.storage.impl.util.DatabaseRegion;

/**
 * 将一个KVDB的某个键映射为一个新的KVDB
 * 比如 B=SubKEDB.getInstance(A,"kkk");
 * 那么对B的读写就会被映射为对A的 kkk.***的读写
 * B.get("aaa") 就相当于 A.get("kkk.aaa");
 */
public class SubKVDB implements KeyValueRegionDatabase {
    private KeyValueDatabase originDB;
    private KeyValueRegionDatabase originDBWithRegion;
    private String path;
    private SubKVDB(KeyValueDatabase originDB,String path){
        if(!DatabaseKeyTool.isKeyValid(path))throw new InvalidDatabaseKeyException();
        this.originDB=originDB;
        this.path=path;
        if(KeyValueRegionDatabase.class.isAssignableFrom(originDB.getClass()))originDBWithRegion= (KeyValueRegionDatabase) originDB;
    }

    /**
     * 针对分Region的KVDB的子数据库映射
     * @param originDB 源数据库
     * @param path 源数据库要映射的键路径
     * @return 返回映射生成的虚拟数据库
     */
    public static KeyValueRegionDatabase getInstance(KeyValueRegionDatabase originDB,String path){
        return new SubKVDB(originDB,path);
    }

    /**
     * 针对不分Region的KVDB的子数据库映射
     * @param originDB 源数据库
     * @param path 源数据库要映射的键路径
     * @return 返回映射生成的虚拟数据库
     */
    public static KeyValueDatabase getInstance(KeyValueDatabase originDB,String path){
        return new SubKVDB(originDB,path);
    }

    @Override
    public String get(String key, DatabaseRegion region) {
        if(originDBWithRegion!=null){
            return originDBWithRegion.get(path+"."+key,region);
        }else return null;
    }

    @Override
    public void set(String key, String value, DatabaseRegion region) {
        if(originDBWithRegion!=null){
            originDBWithRegion.set(path+"."+key,value,region);
        }
    }

    @Override
    public String get(String key) {
        return originDB.get(path+"."+key);
    }

    @Override
    public void set(String key, String value) {
        originDB.set(path+"."+key,value);
    }
}
