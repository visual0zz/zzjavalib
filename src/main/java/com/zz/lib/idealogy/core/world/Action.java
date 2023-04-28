package com.zz.lib.idealogy.core.world;

import com.zz.lib.common.tags.ReadOnly;
import com.zz.lib.idealogy.util.constant.ActionImportance;
import com.zz.lib.idealogy.util.interfaces.AttributeHolder;

@ReadOnly
public class Action extends AttributeHolder {
    ActionImportance getImportance(){
        return null;
    }
}
