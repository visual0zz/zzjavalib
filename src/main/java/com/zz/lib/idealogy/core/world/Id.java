package com.zz.lib.idealogy.core.world;

import com.zz.lib.common.tags.ReadOnly;
import com.zz.lib.idealogy.util.interfaces.AttributeHolder;

import java.util.Objects;

@ReadOnly
public class Id<T extends AttributeHolder> {
    private static long current=10;
    long id;
    public static synchronized <T extends AttributeHolder>Id<T> newId(){
        Id<T> id=new Id<>();
        id.id=++current;
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Id)) return false;
        Id<?> id1 = (Id<?>) o;
        return id == id1.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}