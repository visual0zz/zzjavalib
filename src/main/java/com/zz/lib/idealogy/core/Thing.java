package com.zz.lib.idealogy.core;

public interface Thing {
    Id getId();

    void setAttribute(Attribute attribute);

    <T> T getAttribute(Class<T> attributeType);

    boolean effectedBy(Action action);
}
