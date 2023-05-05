package com.zz.lib.zzscript.impl;

import com.zz.lib.common.CheckUtil;
import com.zz.lib.common.StringUtil;
import com.zz.lib.container.readonly.ReadOnlyList;
import com.zz.lib.container.readonly.ReadOnlyMap;

import java.util.*;

/**
 * 指令输入输出的参数的抽象表示，包含无名的必须参数和具名的可选参数两部分
 */
public class Argument {
    public final ReadOnlyList<Object> requiredData;
    public final ReadOnlyMap<String,Object> optionalData;
    private Argument(List<Object> requiredData,Map<String,Object> optionalData){
        this.requiredData=new ReadOnlyList<>(requiredData);
        this.optionalData=new ReadOnlyMap<>(optionalData);
    }
    public static Argument of(Map<String,Object> optionalData, Object ...requiredData){
        if(optionalData==null){
            optionalData=new HashMap<>();
        }
        CheckUtil.mustNotNull(requiredData);
        Argument d=new Argument(Arrays.asList(requiredData),optionalData);
        return d;
    }
    public static Argument combine(Argument... arguments){
        CheckUtil.mustNotNull(arguments);
        Map<String,Object>optionalData=new HashMap<>();
        List<Object> requiredData= new ArrayList<>();
        for(Argument argument:arguments){
            optionalData.putAll(argument.optionalData);
            requiredData.addAll(argument.requiredData);
        }
        return new Argument(requiredData,optionalData);
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
        private List<Object> requiredData;
        private Map<String,Object> optionalData;
        private Builder(){
            requiredData=new ArrayList<>();
            optionalData=new HashMap<>();
        }
        public Builder required(Object obj){
            requiredData.add(obj);
            return this;
        }
        public Builder optional(String key,Object value){
            optionalData.put(key,value);
            return this;
        }
        public Argument build(){
            Argument d=new Argument(requiredData,optionalData);
            return d;
        }
    }

}
