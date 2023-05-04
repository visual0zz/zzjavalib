package com.zz.lib.idealogy.core.world;
import com.zz.lib.common.tags.ReadOnly;

import java.lang.reflect.Field;

public interface Attribute<T extends Attribute<T>> extends AttributeFilter<T>{
    default Object getValue(Object key){
        try{
            if(!(key instanceof String)){
                return null;
            }
            Field field=this.getClass().getDeclaredField((String) key);
            field.setAccessible(true);
            return field.get(this);
        }catch (NoSuchFieldException e){
            return null;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    default void setValue(Object key,Object value){
        try{
            if(!(key instanceof String)){
                return;
            }
            Field field=this.getClass().getDeclaredField((String) key);
            field.setAccessible(true);
            field.set(this,value);
        }catch (NoSuchFieldException ignore){
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    default Attribute<T> copy(){
        try{
            Attribute<T> result= (Attribute<T>) this.getClass().getDeclaredConstructor().newInstance();
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
    @Override
    boolean equals(Object other);
    @Override
    int hashCode();
    @Override
    default boolean match(T attribute){
        return equals(attribute);
    }
}
