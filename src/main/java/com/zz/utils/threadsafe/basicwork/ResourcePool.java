package com.zz.utils.threadsafe.basicwork;

import org.jetbrains.annotations.Contract;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Random;

public class ResourcePool <T>{
    private final ResourceFactory<T> factory;
    private final SoftReference<T>[] data;//用于储存对象的数组
    private final int capacity;
    private final Random random=new Random();

    /**
     * @param factory 用于生产 T 对象的工厂
     *
     */
    public ResourcePool(ResourceFactory<T> factory) {
        this(factory, 1 );
    }
    /**
     * @param factory 用于生产 T 对象的工厂
     * @param capacity 最多保存多少个 T
     */
    @Contract(value = "null, _-> fail", pure = true)
    public ResourcePool(ResourceFactory<T> factory, int capacity) {
        assert factory!=null;
        assert capacity>0;
        this.factory = factory;
        this.data = (SoftReference<T>[]) new SoftReference[capacity];
        this.capacity=capacity;
    }
    public T getAny(){
        return get(random.nextInt(capacity));
    }
    public T getWithKey(String key){
        return get(key.hashCode()%capacity);
    }

    private T get(int i){//获得第i个位置的对象
        if(data[i]==null){
            synchronized (this){
                if(data[i]==null){//如果对应位置还没有内容
                    T tmp=factory.newInstance();
                    data[i]=new SoftReference<>(tmp);
                    return tmp;
                }
            }
        }
        T tmp=data[i].get();
        if(tmp==null){
            synchronized (this){
                tmp=data[i].get();
                if(tmp==null){//如果存在引用但为空引用
                    tmp=factory.newInstance();
                    data[i]=new SoftReference<>(tmp);
                    return tmp;
                }
            }
        }
        return tmp;
    }

    public interface ResourceFactory<T>{
        T newInstance();
    }
}
