package com.zz.lib.idealogy.core.world;

import com.zz.lib.idealogy.util.interfaces.AttributeHolder;

public interface Thing extends AttributeHolder {
    <T extends Attribute<T>>void setAttribute(Attribute<T> attribute);
    boolean effectedBy(Action action);
}
