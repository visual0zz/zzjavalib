package com.zz.lib.container;

import com.zz.lib.container.tuple.Tuple;
import com.zz.lib.container.tuple.Tuple2;

import java.util.HashMap;

/**
 * @param <V>value 的数据类型
 */
public class MultikeyMap<V> {
    private HashMap<Tuple,V> data;
    public MultikeyMap(){
        data=new HashMap<>();
    }
    public V get(Object ... keys){
        return data.get(new Tuple(keys));
    }
    public void put(V value,Object...keys){
        data.put(new Tuple(keys),value);
    }
}
