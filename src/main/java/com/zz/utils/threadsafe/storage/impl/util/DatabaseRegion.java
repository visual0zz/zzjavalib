package com.zz.utils.threadsafe.storage.impl.util;

/**
 * 用于标记数据的管理方式，
 * Global域 -- 这个域的数据会上传到中央仓库，会和其他电脑交换
 * Local域 -- 这个域的数据仅限本地储存，不会和外界交换
 * Temp域 -- 这个域的数据仅仅存在于内存中，程序停止就会消失
 * Auto域 -- 不指定域时的默认行为，\
 * 当读 Auto域时 相当于依次读取Temp Local Global 任意一个域有对应值就返回，如果都没有就返回null
 * 当写Auto域时 相当于依次寻找Temp Local Global 任意一个域有对应值就修改那个值，如果都没有就修改Global域
 */
public enum DatabaseRegion {Auto, Global, Local, Temp}
