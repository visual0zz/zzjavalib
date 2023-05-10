package com.zz.lib.design.parttern;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

public class Singleton {
    private static ConcurrentHashMap<Class<?>,Object> holder=new ConcurrentHashMap<>();
    public static <T> T get(Class<T> clazz){
        return get(()->clazz.getDeclaredConstructor().newInstance());
    }
    public static <T> T get(ObjectFactory<T> factory){
        T obj=null;
        try{
            Class<?> clazz=factory.getClass().getDeclaredMethod("build").getReturnType();
            obj= (T) holder.get(clazz);
            if(obj==null){
                synchronized (Singleton.class){
                    if(holder.get(clazz)==null){
                        obj=factory.build();
                        holder.put(clazz,obj);
                    }
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return obj;
    }

    public interface ObjectFactory<T>{
        T build()throws Exception;
    }
}
