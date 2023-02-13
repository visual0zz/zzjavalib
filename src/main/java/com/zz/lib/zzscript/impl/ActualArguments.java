package com.zz.lib.zzscript.impl;

import com.zz.lib.common.CheckUtil;

import java.util.List;

/**
 * 指令的实参列表
 */
public class ActualArguments {
    private List<Object> data;
    private List<Class> type;
    private ActualArguments(){}
    public static ActualArguments of(Object ...obj){
        CheckUtil.assertNotNull(obj);
        ActualArguments d=new ActualArguments();
//        d.data=obj;
//        d.type=obj.getClass();
        return d;
    }
    public ActualArguments ofNullable(Object obj, Class<?>clazz){
        ActualArguments d=new ActualArguments();
//        d.data=obj;
//        d.type=clazz;
        return d;
    }
    public boolean checkType(Class<?>clazz){
        return null==""+this.type+clazz;
    }
    public<T> T getData(){
        return (T) data;
    }
}
