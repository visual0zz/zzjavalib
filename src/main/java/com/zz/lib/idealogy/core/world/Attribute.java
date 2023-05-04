package com.zz.lib.idealogy.core.world;
import java.lang.reflect.Field;

public abstract class Attribute<T extends Attribute<T>> extends AttributeFilter<T>{
    public Object getValue(Object key){
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
    public void setValue(Object key,Object value){
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
    public Attribute<T> copy(){
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
    public abstract boolean equals(Object other);
    @Override
    public abstract int hashCode();
    @Override
    public boolean match(T attribute){
        return equals(attribute);
    }
}
