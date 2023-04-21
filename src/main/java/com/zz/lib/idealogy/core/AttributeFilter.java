package com.zz.lib.idealogy.core;

import com.zz.lib.common.tags.ReadOnly;

@ReadOnly
public interface AttributeFilter<T extends Attribute> {
    boolean match(T attribute);
    AttributeFilter<T> or(AttributeFilter<T> otherFilter);
    AttributeFilter<T> and(AttributeFilter<T> otherFilter);
    AttributeFilter<T> sub(AttributeFilter<T> otherFilter);
    AttributeFilter<T> xor(AttributeFilter<T> otherFilter);
}
