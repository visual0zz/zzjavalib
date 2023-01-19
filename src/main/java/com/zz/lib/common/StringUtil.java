package com.zz.lib.common;

import com.zz.lib.common.exception.DataSizeException;

public final class StringUtil {
    /**
     * 用格式字符串拼接字符串
     * @param format 格式字符串类似"xxx={},xxx={}" 这种，使用大括号表示填充数据的位置
     * @param params 用于填充格式字符串空位的数据
     * @return 拼接后的字符串
     */
    public static String format(String format,Object ... params){
        StringBuilder builder=new StringBuilder();
        int placeHolderCount=0;
        for(int i=0;i<format.length();i++){
            char currentChar=format.charAt(i);
            if(currentChar=='{'&&format.charAt(i+1)=='}'){
                if(placeHolderCount<params.length){
                    builder.append(params[placeHolderCount].toString());
                }
                placeHolderCount++;
                i++;
            }else{
                builder.append(currentChar);
            }
        }
        if(placeHolderCount!=params.length){
            throw new DataSizeException("need "+placeHolderCount+" param,but got "+params.length);
        }
        return builder.toString();
    }
}
