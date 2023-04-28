package com.zz.lib.idealogy.core.world;

import com.zz.lib.idealogy.util.interfaces.AttributeHolder;

public abstract class Thing extends AttributeHolder {
    boolean effectedBy(Action action) {
        return false;
    }
}
