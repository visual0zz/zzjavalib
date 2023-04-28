package com.zz.lib.idealogy.util.constant;

import com.zz.lib.idealogy.core.world.Attribute;

public enum ActionImportance implements Attribute<ActionImportance> {
    CORE,VITAL,ESSENTIAL,IMPORTANT,MEDIUM,NORMAL,MINOR,TRIVIAL;
    @Override
    public Object getValue(Object key) {
        if("name".equals(key)){
            return name();
        }else if("code".equals(key)){
            return ordinal();
        }
        return null;
    }

    @Override
    public ActionImportance copy() {
        return this;
    }
}
