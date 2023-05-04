package com.zz.lib.container.readonly;

import com.zz.lib.common.exception.InvalidOperationException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ReadOnlyMap<T,V> implements Map<T,V> {
    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return data.get(key);
    }

    @Override
    public V put(T key, V value) {
        throw new InvalidOperationException("read only.");
    }

    @Override
    public V remove(Object key) {
        throw new InvalidOperationException("read only.");
    }

    @Override
    public void putAll(Map<? extends T, ? extends V> m) {
        throw new InvalidOperationException("read only.");
    }

    @Override
    public void clear() {
        throw new InvalidOperationException("read only.");
    }

    @Override
    public ReadOnlySet<T> keySet() {//todo
        return new ReadOnlySet<>(data.keySet());
    }

    @Override
    public Collection<V> values() {
        return data.values();
    }

    @Override
    public ReadOnlySet<Entry<T, V>> entrySet() {
        return new ReadOnlySet<>(data.entrySet());
    }

    @Override
    public boolean equals(Object o) {
        return data.equals(o);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    Map<T,V> data;
    public ReadOnlyMap(Map<T,V> map){
        this.data=map;
    }
}
