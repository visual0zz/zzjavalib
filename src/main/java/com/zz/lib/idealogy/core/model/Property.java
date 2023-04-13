package com.zz.lib.idealogy.core.model;

import com.zz.lib.container.Color;
import com.zz.lib.container.Vector;

public class Property<T>{

    public enum Name{
        POSITION("位置", Vector.class),COLOR("颜色", Color.class),;
        String meaning;
        Class<?> valueType;
        Name(String meaning,Class<?>valueType){
            this.meaning=meaning;
            this.valueType=valueType;
        }
    }
}
