package com.zz.lib.idealogy.util.interfaces;
import com.zz.lib.idealogy.core.world.Attribute;
import com.zz.lib.reflection.ReflectUtil;

import java.lang.reflect.Field;

public abstract class AttributeHolder {
    public <T extends Attribute> T getAttribute(Class<T> attributeType) {
        try{
            for (Field field : ReflectUtil.getDeclaredFieldsWithSuper(getClass())) {
                if (field.getType().equals(attributeType)) {
                    field.setAccessible(true);
                    return (T)field.get(this);
                }
            }
            return null;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public <T extends Attribute> Object getAttributeValue(Class<T> attributeType, Object key) {
        T attribute = getAttribute(attributeType);
        if (attribute == null) {
            return null;
        }
        return attribute.getValue(key);
    }
}
