package com.zz.utils.threadsafe.storage.impl.util;

public class PasswordGenerator {
    private static final char[] codeTableH={//高半字节使用的表
            'a','c','d','e','f','g','h','j',
            'k','m','n','p','q','r','t','w'};
    private static final char[] codeTableL={//低半字节使用的表
            'x','y','3','4','6','7','9','#',
            '*','!','+','-','?','>','<','.'
    };
    public static String encode(byte[]data){
        assert data!=null;
        StringBuilder builder=new StringBuilder();
        for(byte b:data){
            byte h= (byte) ((b>>4)&0x0f);//高位在前低位在后
            byte l= (byte) (b&0x0f);
            char hc=codeTableH[h];
            char lc=codeTableL[l];
            builder.append(hc);
            builder.append(lc);
        }
        return builder.toString();
    }
}
