package com.zz.lib.idealogy.interfaces;

import com.zz.lib.idealogy.core.Attribute;

public interface AttributeHolder {
    <T extends Attribute<T>> T getAttribute(Class<T> attributeType);
    <T extends Attribute<T>> Object getAttributeValue(Class<T> attributeType,Object key);
}
