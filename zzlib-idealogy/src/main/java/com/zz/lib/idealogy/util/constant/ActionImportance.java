package com.zz.lib.idealogy.util.constant;

import com.zz.lib.idealogy.core.world.Attribute;

public class ActionImportance extends Attribute {
    public static ActionImportance CORE,VITAL,ESSENTIAL,IMPORTANT,MEDIUM,NORMAL,MINOR,TRIVIAL;
    private ActionImportance(String name,Integer code){
        this.name=name;
        this.code=code;
    }
    static {
        CORE=new ActionImportance("CORE",0);
        VITAL=new ActionImportance("VITAL",0);
        ESSENTIAL=new ActionImportance("ESSENTIAL",0);
        IMPORTANT=new ActionImportance("IMPORTANT",0);
        MEDIUM=new ActionImportance("MEDIUM",0);
        NORMAL=new ActionImportance("NORMAL",0);
        MINOR=new ActionImportance("MINOR",0);
        TRIVIAL=new ActionImportance("TRIVIAL",0);
    }
    private String name;
    private Integer code;
    @Override
    public ActionImportance copy() {
        return this;
    }

    @Override
    public boolean equals(Object other) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
