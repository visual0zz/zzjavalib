package com.zz.lib.idealogy.core.world;

import com.zz.lib.common.tags.ReadOnly;
import com.zz.lib.idealogy.util.constant.ActionImportance;
import com.zz.lib.idealogy.util.interfaces.AttributeHolder;

@ReadOnly
public abstract class Action extends AttributeHolder {
    private final ActionImportance importance;
    public Action(ActionImportance importance){
        this.importance=importance;
    }
    public ActionImportance getImportance(){
        return importance;
    }

}
