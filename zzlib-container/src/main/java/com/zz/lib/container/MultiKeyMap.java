package com.zz.lib.container;

import com.zz.lib.container.tuple.Tuple;

import java.util.HashMap;

/**
 * @param <V>value 的数据类型
 */
public class MultiKeyMap<V> {
    private HashMap<Tuple,V> data=new HashMap<>();
    public V get(Object ... keys){
        return data.get(new Tuple(keys));
    }
    public void put(V value,Object...keys){
        data.put(new Tuple(keys),value);
    }
}
