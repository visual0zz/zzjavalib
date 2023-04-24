package com.zz.lib.idealogy.interfaces;

import com.zz.lib.idealogy.core.Attribute;

public interface AttributeHolder {
    <T extends Attribute> T getAttribute(Class<T> attributeType);
}
