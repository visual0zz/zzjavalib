package com.zz.lib.container.readonly;

import com.zz.lib.common.exception.InvalidOperationException;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class ReadOnlySet<T> implements Set<T> {
    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return data.contains(o);
    }

    @Override
    public ReadOnlyIterator<T> iterator() {
        return new ReadOnlyIterator<>(data.iterator());
    }

    @Override
    public Object[] toArray() {
        return data.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return data.toArray(a);
    }

    @Override
    public boolean add(T t) {
        throw new InvalidOperationException("read only.");
    }

    @Override
    public boolean remove(Object o) {
        throw new InvalidOperationException("read only.");
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return data.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return data.addAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new InvalidOperationException("read only.");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new InvalidOperationException("read only.");
    }

    @Override
    public void clear() {
        throw new InvalidOperationException("read only.");
    }

    @Override
    public boolean equals(Object o) {
        return data.equals(o);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    Set<T>data;
    public ReadOnlySet(Set<T>set){
        data=set;
    }
}
