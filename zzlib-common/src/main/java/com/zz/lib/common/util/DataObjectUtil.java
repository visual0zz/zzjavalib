package com.zz.lib.common.util;

import com.zz.lib.common.tags.Incomplete;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 处理数值对象的常用功能
 */
@Incomplete
public class DataObjectUtil {
    static public boolean deepEquals(Object o1,Object o2){
        return false;
    }

    /**
     * 将一个数据对象的内部属性使用map来表示,所有public的属性或者getter函数都会被用来获得属性
     * @param obj 数据对象(不能定义在java.*包下面) 应当是简单的数据对象，不应当是容器、数组或者别的复杂对象
     * @return 根据数据对象的属性转化为的map
     */
    static public Map<String,Object> extract(Object obj){
        Class<?>currentClazz=obj.getClass();
        Map<String,Object>properties=new HashMap<>();

        return null;
    }
    static public Map<String,Object> deepExtract(Object obj){
        return null;
    }
    public <T> T deepCopy(T in){
        return null;
    }
    public void copyProperties(Object from,Object to){
    }
    String getterName2PropertyName(String getterName){
        return null;
    }

    /**
     * 获得所有public的普通属性，相比Class.getFields的差别就是基类链条不会延伸到java包下面,使得一些系统类中定义的奇怪属性不会被读出来
     * @param clazz 类
     * @return 属性列表
     */
    static List<Field> getAllNormalFields(Class<?>clazz){
        List<Field> fields=new ArrayList<>();
        Class<?>currentClazz=clazz;
        while (!currentClazz.getPackage().getName().startsWith("java.")){
            for(Field field:currentClazz.getDeclaredFields()){
                if(Modifier.isPublic(field.getModifiers())){
                    fields.add(field);
                }
            }
            currentClazz=currentClazz.getSuperclass();
        }
        return fields;
    }
    /**
     * 获得所有public的普通方法，相比Class.getMethods的差别就是基类链条不会延伸到java包下面,使得一些系统类中定义的奇怪方法不会被读出来
     * @param clazz 类
     * @return 方法列表
     */
    static List<Method> getAllNormalMethods(Class<?>clazz){
        List<Method> methods=new ArrayList<>();
        Class<?>currentClazz=clazz;
        while (!currentClazz.getPackage().getName().startsWith("java.")){
            for(Method method: currentClazz.getDeclaredMethods()){
                if(Modifier.isPublic(method.getModifiers())){
                    methods.add(method);
                }
            }
            currentClazz=currentClazz.getSuperclass();
        }
        return methods;
    }
}
