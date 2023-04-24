package com.zz.lib.idealogy.core;
import com.zz.lib.common.tags.ReadOnly;

import java.lang.reflect.Field;

@ReadOnly
public interface Attribute {
    default Object getValue(Object key){
        try{
            Field field=this.getClass().getDeclaredField((String) key);
            field.setAccessible(true);
            return field.get(this);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    default <T extends Attribute> T copy(){
        try{
            T result= (T) this.getClass().getDeclaredConstructor().newInstance();
            Field[] fields=this.getClass().getDeclaredFields();
            for(Field field:fields){
                field.setAccessible(true);
                field.set(result,field.get(this));
            }
            return result;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
