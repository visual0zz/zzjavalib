package com.zz.lib.json.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class JavaObject2JsonStr{
    boolean formatted;
    String construct(Object object,int level){
        if(object==null){
            return null;
        }else if(object instanceof Integer){
            return construct((Integer) object,level);
        }else if(object instanceof Long){
            return construct((Long) object,level);
        }else if(object instanceof BigDecimal){
            return construct((BigDecimal) object,level);
        }else if(object instanceof List){
            return construct((List<Object>) object,level);
        }else if(object instanceof Map){
            return construct((Map<String, Object>) object,level);
        }
        return "";
    }
    String construct(List<Object> array,int level){
        StringBuilder result=new StringBuilder();
        result.append("{");
        if(formatted){
            result.append("\n");
        }
        for(Object obj:array){
            if(obj!=null){
                leadingSpace(result,level);
                result.append(construct(obj,level+1));
                if(formatted){
                    result.append("\n");
                }
            }
        }
        if(result.charAt(result.length()-1)==','){
            result.deleteCharAt(result.length()-1);
        }
        return result.toString();
    }
    String construct(Map<String,Object> map,int level){
        StringBuilder result=new StringBuilder();
        result.append("{");
        if(formatted){
            result.append("\n");
        }
        for(Map.Entry<String,Object> entry:map.entrySet()){
            if(entry.getValue()!=null){
                leadingSpace(result,level);
                result.append('"')
                        .append(entry.getKey())
                        .append("\": ")
                        .append(construct(entry.getValue(),level+1))
                        .append(',');
                if(formatted){
                    result.append("\n");
                }
            }
        }
        if(result.charAt(result.length()-1)==','){
            result.deleteCharAt(result.length()-1);
        }
        return result.toString();
    }
    String construct(Integer number,int level){
        StringBuilder result=new StringBuilder();
        leadingSpace(result,level);
        result.append(number);
        return result.toString();
    }
    String construct(Long number,int level){
        StringBuilder result=new StringBuilder();
        leadingSpace(result,level);
        result.append(number);
        return result.toString();
    }
    String construct(BigDecimal number,int level){
        number=number.stripTrailingZeros();
        StringBuilder result=new StringBuilder();
        leadingSpace(result,level);
        if(number.abs().compareTo(new BigDecimal("0.1"))>0
                && number.abs().compareTo(new BigDecimal("1e8"))<0 ){
            result.append(number.toPlainString());
        }else{
            result.append(number.toString());
        }
        return result.toString();
    }
    private void leadingSpace(StringBuilder builder,int level){
        if(formatted){
            for(int i=0;i<level;i++){
                builder.append("  ");
            }
        }
    }
}
