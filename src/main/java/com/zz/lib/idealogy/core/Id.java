package com.zz.lib.idealogy.core;

import com.zz.lib.common.tags.ReadOnly;

@ReadOnly
public interface Id<T> {
    boolean equals(Object other);

    int hashCode();
}