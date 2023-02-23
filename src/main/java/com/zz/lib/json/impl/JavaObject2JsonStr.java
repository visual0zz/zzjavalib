package com.zz.lib.json.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaObject2JsonStr{
    private static JavaObject2JsonStr BUILDER=new JavaObject2JsonStr(false);
    private static JavaObject2JsonStr FORMATTED_BUILDER=new JavaObject2JsonStr(false);

    JavaObject2JsonStr(boolean formatted){
        this.formatted=formatted;
    }
    public static String toJson(Object object){
        return BUILDER.construct(object,1);
    }
    public static String toFormatJson(Object object){
        return FORMATTED_BUILDER.construct(object,1);
    }
    boolean formatted;
    String construct(Object object,int level){
        if(object==null){
            return null;
        }else if(object instanceof Integer){
            return String.valueOf((Integer) object);
        }else if(object instanceof Long){
            return String.valueOf((Long) object);
        }else if(object instanceof BigDecimal){
            return constructDecimal((BigDecimal) object);
        }else if(object instanceof List){
            return constructArray((List<Object>) object,level);
        }else if(object instanceof Map){
            return constructMap((Map<String, Object>) object,level);
        }else if(object instanceof byte[]){
            List<Object> array=new ArrayList<>();
            for(byte b:(byte[])object){
                array.add((int)b);
            }
            return constructArray(array,level);
        }else if(object instanceof short[]){
            List<Object> array=new ArrayList<>();
            for(short b:(short[])object){
                array.add((int)b);
            }
            return constructArray(array,level);
        }else if(object instanceof int[]){
            List<Object> array=new ArrayList<>();
            for(int b:(int[])object){
                array.add(b);
            }
            return constructArray(array,level);
        }else if(object instanceof long[]){
            List<Object> array=new ArrayList<>();
            for(long b:(long[])object){
                array.add(b);
            }
            return constructArray(array,level);
        }else if(object instanceof float[]){
            List<Object> array=new ArrayList<>();
            for(float b:(float[])object){
                array.add(BigDecimal.valueOf(b));
            }
            return constructArray(array,level);
        }else if(object instanceof double[]){
            List<Object> array=new ArrayList<>();
            for(double b:(double[])object){
                array.add(BigDecimal.valueOf(b));
            }
            return constructArray(array,level);
        }else if(object instanceof char[]){
            return constructString(String.valueOf((char[])object));
        }else if(object instanceof String){
            //todo 编码字符串，进行特殊字符的转义
            return constructString((String) object);
        }
        //todo 判断对象是直接继承Object的简单对象，直接提取内部属性
        return "";
    }
    String constructArray(List<Object> array, int level){
        StringBuilder result=new StringBuilder();
        result.append("[");
        if(formatted){
            result.append("\n");
        }
        for(int i=0;i<array.size();i++){
            Object obj=array.get(i);
            if(obj!=null){
                leadingSpace(result,level);
                result.append(construct(obj,level+1));
                if(i<array.size()-1){
                    result.append(',');
                }
                if(formatted){
                    result.append("\n");
                }
            }
        }
        leadingSpace(result,level-1);
        result.append(']');
        return result.toString();
    }
    String constructMap(Map<String,Object> map, int level){
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

    String constructDecimal(BigDecimal number){
        number=number.stripTrailingZeros();
        StringBuilder result=new StringBuilder();
        if(number.abs().compareTo(new BigDecimal("0.1"))>0
                && number.abs().compareTo(new BigDecimal("1e8"))<0 ){
            result.append(number.toPlainString());
        }else{
            result.append(number);
        }
        return result.toString();
    }
    String constructString(String str){
        return '"' + str + '"';
    }
    private void leadingSpace(StringBuilder builder,int level){
        if(formatted){
            for(int i=0;i<level;i++){
                builder.append("  ");
            }
        }
    }
}
