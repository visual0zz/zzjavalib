package com.zz.lib.json;

import com.zz.lib.json.impl.JavaObject2JsonStr;

/**
 * 简易json处理工具
 * 使用java标准库容器来表示json结构
 * 会处理json的转义字符
 * array -> List
 * object -> Map
 * number -> Integer,Long,BigDecimal
 * string -> String
 *
 */
public class JsonUtil {
    /**
     * java对象构成的数据结构转化为json字符串
     * 输入的java对象如果为普通对象则会读取属性来构成jsonObject
     * 如果为容器则会构成jsonArray 或者jsonObject
     * 
     * @param object 数据对象
     * @return json字符串
     */
    public static String toJsonStr(Object object){
        return JavaObject2JsonStr.toJson(object);
    }

    /**
     * java对象构成的数据结构转化为易读的json字符串，使用缩进和回车来形成格式化的，易读的json字符串
     * 输入的java对象如果为普通对象则会读取属性来构成jsonObject
     * 如果为容器则会构成jsonArray 或者jsonObject
     *
     * @param object 数据对象
     * @return 带缩进和回车的易读json字符串
     */
    public static String toReadableJsonStr(Object object){
        return JavaObject2JsonStr.toJson(object);
    }

    public Object getObject(Object object,String jsonPath){
        return null;
    }
    public String getString(Object object,String jsonPath){
        return null;
    }
}
