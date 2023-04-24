package com.zz.lib.idealogy.core;

import com.zz.lib.common.tags.ReadOnly;

@ReadOnly
public interface Attribute {
    Object getValue(Object key);
}
