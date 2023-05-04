package com.zz.lib.idealogy.core.world;

import com.zz.lib.common.tags.ReadOnly;

@ReadOnly
public interface AttributeFilter{
    boolean match(Attribute attribute);
    default AttributeFilter or(AttributeFilter otherFilter){
        AttributeFilter self=this;
        return new AttributeFilterJoiner() {
            @Override
            public boolean match(Attribute attribute) {
                return self.match(attribute)||otherFilter.match(attribute);
            }
        };
    }
    default AttributeFilter  and(AttributeFilter  otherFilter){
        AttributeFilter self=this;
        return new AttributeFilterJoiner() {
            @Override
            public boolean match(Attribute attribute) {
                return self.match(attribute)&&otherFilter.match(attribute);
            }
        };
    }
    default AttributeFilter  sub(AttributeFilter  otherFilter){
        AttributeFilter self=this;
        return new AttributeFilterJoiner() {
            @Override
            public boolean match(Attribute attribute) {
                return self.match(attribute)&&!otherFilter.match(attribute);
            }
        };
    }
    default AttributeFilter  xor(AttributeFilter  otherFilter){
        AttributeFilter self=this;
        return new AttributeFilterJoiner() {
            @Override
            public boolean match(Attribute attribute) {
                return self.match(attribute)^otherFilter.match(attribute);
            }
        };
    }
    abstract class AttributeFilterJoiner implements AttributeFilter {
    }
}
