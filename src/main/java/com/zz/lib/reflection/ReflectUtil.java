package com.zz.lib.reflection;

import com.zz.lib.common.ArrayUtil;
import com.zz.lib.common.CheckUtil;

import java.lang.reflect.Field;

public class ReflectUtil {

    /**
     * 获得一个类中所有字段列表，直接反射获取，无缓存<br>
     * 如果子类与父类中存在同名字段，则这两个字段同时存在，子类字段在前，父类字段在后。
     *
     * @param beanClass            类
     * @param withSuperClassFields 是否包括父类的字段列表
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Field[] getFields(Class<?> beanClass, boolean withSuperClassFields) throws SecurityException {
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
            searchType = withSuperClassFields ? searchType.getSuperclass() : null;
        }

        return allFields;
    }
}
