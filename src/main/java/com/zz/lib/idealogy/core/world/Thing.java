package com.zz.lib.idealogy.core.world;

import com.zz.lib.idealogy.util.interfaces.AttributeHolder;
import com.zz.lib.reflection.ReflectUtil;

import java.lang.reflect.Field;

public abstract class Thing extends AttributeHolder {
    boolean effectedBy(Action action) {
        return false;
    }

    @Override
    public <T extends Attribute> T getAttribute(Class<T> attributeType) {
        return super.getAttribute(attributeType);
    }
    public <T extends Attribute>void setAttribute(T attribute){
        try{
            for (Field field : ReflectUtil.getDeclaredFieldsWithSuper(getClass())) {
                if (field.getType().isAssignableFrom(attribute.getClass())) {
                    field.setAccessible(true);
                    field.set(this,attribute);
                    return;
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public <T extends Attribute> void setAttributeValue(Class<T> attributeType, Object key,Object value) {
        try{
            for (Field field : ReflectUtil.getDeclaredFieldsWithSuper(getClass())) {
                if (field.getType().isAssignableFrom(attributeType)) {
                    field.setAccessible(true);
                    ((T)field.get(this)).setValue(key,value);
                    return;
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
