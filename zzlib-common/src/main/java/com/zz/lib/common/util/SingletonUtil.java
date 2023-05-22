package com.zz.lib.common.util;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 懒加载单例模式的实现，兼容并发
 */
public class SingletonUtil {
    private static final ConcurrentHashMap<Class<?>,Object> holder=new ConcurrentHashMap<>();
    public static <T> T get(Class<T> clazz){
        return get(()->clazz.getDeclaredConstructor().newInstance());
    }
    public static <T> T get(ObjectFactory<T> factory){
        T obj=null;
        try{
            Class<?> clazz=factory.getClass().getDeclaredMethod("build").getReturnType();
            obj= (T) holder.get(clazz);
            if(obj==null){
                synchronized (clazz){
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
