package com.zz.lib.idealogy.core.world;

import com.zz.lib.common.tags.ReadOnly;
import com.zz.lib.idealogy.util.constant.ActionImportance;
import com.zz.lib.idealogy.util.interfaces.AttributeHolder;

@ReadOnly
public class Action implements AttributeHolder {
    ActionImportance getImportance(){
        return null;
    }

    @Override
    public <T extends Attribute<T>> T getAttribute(Class<T> attributeType) {
        return null;
    }

    @Override
    public <T extends Attribute<T>> Object getAttributeValue(Class<T> attributeType, Object key) {
        return null;
    }
}
