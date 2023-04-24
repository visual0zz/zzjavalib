package com.zz.lib.idealogy.core;

import com.zz.lib.common.tags.ReadOnly;

@ReadOnly
public interface AttributeFilter<T extends Attribute> {
    boolean match(T attribute);
    default AttributeFilter<T> or(AttributeFilter<T> otherFilter){
        AttributeFilter<T>self=this;
        return (AttributeFilterJoiner<T>) attribute -> self.match(attribute)||otherFilter.match(attribute);
    }
    default AttributeFilter<T> and(AttributeFilter<T> otherFilter){
        AttributeFilter<T>self=this;
        return (AttributeFilterJoiner<T>) attribute -> self.match(attribute)&&otherFilter.match(attribute);
    }
    default AttributeFilter<T> sub(AttributeFilter<T> otherFilter){
        AttributeFilter<T>self=this;
        return (AttributeFilterJoiner<T>) attribute -> self.match(attribute)&&!otherFilter.match(attribute);
    }
    default AttributeFilter<T> xor(AttributeFilter<T> otherFilter){
        AttributeFilter<T>self=this;
        return (AttributeFilterJoiner<T>) attribute -> self.match(attribute)^otherFilter.match(attribute);
    }
    interface AttributeFilterJoiner<T extends Attribute> extends AttributeFilter<T>{
    }
}
