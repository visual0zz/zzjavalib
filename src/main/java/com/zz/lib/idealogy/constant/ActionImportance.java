package com.zz.lib.idealogy.constant;

import com.zz.lib.idealogy.core.Attribute;

public enum ActionImportance implements Attribute {
    CORE,VITAL,ESSENTIAL,IMPORTANT,MEDIUM,NORMAL,BACKGROUND,NOTHING;
    @Override
    public Object getValue(Object key) {
        if("name".equals(key)){
            return name();
        }else if("code".equals(key)){
            return ordinal();
        }
        return null;
    }
}
