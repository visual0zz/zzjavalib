package com.zz.lib.idealogy.core;

import com.zz.lib.idealogy.interfaces.AttributeHolder;

public interface Thing extends AttributeHolder {
    <T extends Attribute<T>>void setAttribute(Attribute<T> attribute);
    boolean effectedBy(Action action);
}
