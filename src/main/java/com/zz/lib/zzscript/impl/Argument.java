package com.zz.lib.zzscript.impl;

import com.zz.lib.common.CheckUtil;
import com.zz.lib.common.StringUtil;

import java.util.*;

/**
 * 指令输入输出的参数的抽象表示，包含无名的必须参数和具名的可选参数两部分
 */
public class Argument {
    private List<Object> requiredData;
    private Map<String,Object> optionalData;
    private Argument(){}
    public static Argument of(Map<String,Object> optionalData, Object ...requiredData){
        if(optionalData==null){
            optionalData=new HashMap<>();
        }
        CheckUtil.mustNotNull(requiredData);
        Argument d=new Argument();
        d.optionalData=optionalData;
        d.requiredData= Arrays.asList(requiredData);
        return d;
    }
    public static Argument of(Argument... arguments){
        CheckUtil.mustNotNull(arguments);
        Argument d=new Argument();
        d.optionalData=new HashMap<>();
        d.requiredData= new ArrayList<>();
        for(Argument argument:arguments){
            d.optionalData.putAll(argument.optionalData);
            d.requiredData.addAll(argument.requiredData);
        }
        return d;
    }

    @Override
    public String toString() {
        StringBuilder builder=new StringBuilder("{ ");
        for(Object obj:requiredData){
            if(obj instanceof String){
                builder.append(StringUtil.escapeString((String) obj)).append(" ");
            }
            builder.append("<").append(obj.toString()).append(">").append(" ");
        }
        for(Map.Entry<String,Object> entry:optionalData.entrySet()){
            builder.append("--").append(entry.getKey()).append(" ");
            if(entry.getValue() instanceof String){
                builder.append(StringUtil.escapeString((String) entry.getValue())).append(" ");
            }
            builder.append("<").append(entry.getValue().toString()).append(">").append(" ");
        }
        builder.append("}");
        return builder.toString();
    }

    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        private Builder(){

        }
    }

}
