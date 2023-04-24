package com.zz.lib.idealogy.core;

public interface Thing {

    void setAttribute(Attribute attribute);

    <T extends Attribute> T getAttribute(Class<T> attributeType);
    <T extends Attribute> Object getAttributeValue(Class<T> attributeType,Object key);

    boolean effectedBy(Action action);
}
