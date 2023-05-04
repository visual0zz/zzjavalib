package com.zz.lib.idealogy.core.world;

import com.zz.lib.common.tags.ReadOnly;

@ReadOnly
public abstract class AttributeFilter<T extends Attribute<T>> {
    public abstract boolean match(T attribute);
    public AttributeFilter<T> or(AttributeFilter<T> otherFilter){
        AttributeFilter<T>self=this;
        return new AttributeFilterJoiner() {
            @Override
            public boolean match(T attribute) {
                return self.match(attribute)||otherFilter.match(attribute);
            }
        };
    }
    public AttributeFilter<T> and(AttributeFilter<T> otherFilter){
        AttributeFilter<T>self=this;
        return new AttributeFilterJoiner() {
            @Override
            public boolean match(T attribute) {
                return self.match(attribute)&&otherFilter.match(attribute);
            }
        };
    }
    public AttributeFilter<T> sub(AttributeFilter<T> otherFilter){
        AttributeFilter<T>self=this;
        return new AttributeFilterJoiner() {
            @Override
            public boolean match(T attribute) {
                return self.match(attribute)&&!otherFilter.match(attribute);
            }
        };
    }
    public AttributeFilter<T> xor(AttributeFilter<T> otherFilter){
        AttributeFilter<T>self=this;
        return new AttributeFilterJoiner() {
            @Override
            public boolean match(T attribute) {
                return self.match(attribute)^otherFilter.match(attribute);
            }
        };
    }
    abstract class AttributeFilterJoiner extends AttributeFilter<T>{
    }
}
