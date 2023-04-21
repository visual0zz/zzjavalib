package com.zz.lib.idealogy.core;

import com.zz.lib.common.tags.ReadOnly;
import com.zz.lib.idealogy.constant.ActionImportance;

@ReadOnly
public interface Action {
    ActionImportance getImportance();
}
