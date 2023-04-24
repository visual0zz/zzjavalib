package com.zz.lib.idealogy.core;

import com.zz.lib.common.tags.ReadOnly;
import com.zz.lib.idealogy.constant.ActionImportance;
import com.zz.lib.idealogy.interfaces.AttributeHolder;

@ReadOnly
public interface Action extends AttributeHolder {
    ActionImportance getImportance();
}
