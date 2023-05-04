package com.zz.lib.reflection;

import com.zz.lib.common.ArrayUtil;
import com.zz.lib.common.CheckUtil;

import java.lang.reflect.Field;
import java.nio.file.NoSuchFileException;

public class ReflectUtil {

    /**
     * 获得一个类中所有字段列表，直接反射获取，无缓存<br>
     * 如果子类与父类中存在同名字段，则这两个字段同时存在，子类字段在前，父类字段在后。
     * @param beanClass 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Field[] getDeclaredFieldsWithSuper(Class<?> beanClass) throws SecurityException {
        CheckUtil.mustNotNull(beanClass);

        Field[] allFields = null;
        Class<?> searchType = beanClass;
        Field[] declaredFields;
        while (searchType != null) {
            declaredFields = searchType.getDeclaredFields();
            if (null == allFields) {
                allFields = declaredFields;
            } else {
                allFields = ArrayUtil.concat(allFields, declaredFields);
            }
            searchType =searchType.getSuperclass();
        }

        return allFields;
    }

    /**
     * 获得一个类中某个字段，包含所有上级类
     * @param beanClass 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Field getDeclaredFieldWithSuper(Class<?> beanClass,String fieldName)throws NoSuchFieldException {
        CheckUtil.mustNotNull(beanClass);
        Class<?> searchType = beanClass;
        while (true) {
            try {
                return searchType.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                searchType =searchType.getSuperclass();
                if(searchType==null){
                    throw e;
                }
            }
        }
    }
}
