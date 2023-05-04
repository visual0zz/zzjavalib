package com.zz.lib.container.readonly;

import com.zz.lib.common.exception.InvalidOperationException;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Consumer;

public class ReadOnlyIterator<T> implements Iterator<T> {
    @Override
    public boolean hasNext() {
        return data.hasNext();
    }

    @Override
    public T next() {
        return data.next();
    }

    @Override
    public void remove() {
        throw new InvalidOperationException("read only");
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        Objects.requireNonNull(action);
        while (data.hasNext())
            action.accept(data.next());
    }

    Iterator<T> data;
    public ReadOnlyIterator(Iterator<T> iterator){
        data=iterator;
    }
}
