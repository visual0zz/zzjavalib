package com.zz.utils.threadsafe.encode;


/**
 * 字符表经过筛选，去掉了所有易于 阅读混淆 听说混淆 草体书写混淆
 *  和 不易口述的字符 (如特殊字符和大写字母)
 *  用于以 人类兼容 的方式来传递二进制信息。
 */
public class ZZBase16 {
    private static final char[] codeTable={//编码使用的字符表
            'a','c','d','e','f','g','h','x',
            'k','m','y','4','8','7','t','w'};

    public static String encode(byte[]data){
        assert data!=null;
        StringBuilder builder=new StringBuilder();
        for(byte b:data){
            byte h= (byte) ((b>>4)&0x0f);//高位在前低位在后
            byte l= (byte) (b&0x0f);
            char hc=codeTable[h];
            char lc=codeTable[l];
            builder.append(hc);
            builder.append(lc);
        }
        return builder.toString();
    }

    public static byte[] decode(String data){
        assert data!=null;
        int length=data.length();
        byte[]result=new byte[length/2];
        try{
            for(int i=0;i<length;i+=2){
                char hc=data.charAt(i);
                char lc=data.charAt(i+1);
                byte h=-1,l=-1;
                for(int j=0;j<16;j++){//寻找高字节对应值
                    if(codeTable[j]==hc){
                        h= (byte) j;
                        break;
                    }
                }
                for(int j=0;j<16;j++){//寻找低字节对应值
                    if(codeTable[j]==lc){
                        l= (byte) j;
                        break;
                    }
                }
                if(h==-1 || l==-1){return null;}//如果没有找到对应字符，就返回空
                result[i/2]= (byte) ((h<<4)|l);
            }
        }catch (Exception e){
            return null;
        }
        return result;
    }
}
