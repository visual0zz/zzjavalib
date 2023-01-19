package com.zz.lib.encode;

/**
 * 没什么卵用的编码 用来将数据编码成非常难以阅读的形式
 */
public class ZZOoO {

    private static final char[] codeTable={//编码使用的字符表
            'b','p','q','d'};

    public static String encode(byte[]data){
        assert data!=null;
        StringBuilder builder=new StringBuilder();
        for(byte b:data){
            byte h1= (byte) ((b>>6)&0x03);//高位在前低位在后
            byte h2= (byte) ((b>>4)&0x03);
            byte h3= (byte) ((b>>2)&0x03);
            byte h4= (byte) ((b)&0x03);
            char h1c=codeTable[h1];
            char h2c=codeTable[h2];
            char h3c=codeTable[h3];
            char h4c=codeTable[h4];
            builder.append(h1c);
            builder.append(h2c);
            builder.append(h3c);
            builder.append(h4c);
        }
        return builder.toString();
    }

    public static byte[] decode(String data){
        assert data!=null;
        int length=data.length();
        byte[]result=new byte[length/4];
        try{
            for(int i=0;i<length;i+=4){
                char h1c=data.charAt(i);
                char h2c=data.charAt(i+1);
                char h3c=data.charAt(i+2);
                char h4c=data.charAt(i+3);
                byte h1=-1,h2=-1,h3=-1,h4=-1;
                for(int j=0;j<4;j++){//寻找字节对应值
                    if(codeTable[j]==h1c){
                        h1= (byte) j;
                        break;
                    }
                }
                for(int j=0;j<4;j++){//寻找字节对应值
                    if(codeTable[j]==h2c){
                        h2= (byte) j;
                        break;
                    }
                }
                for(int j=0;j<4;j++){//寻找字节对应值
                    if(codeTable[j]==h3c){
                        h3= (byte) j;
                        break;
                    }
                }
                for(int j=0;j<4;j++){//寻找字节对应值
                    if(codeTable[j]==h4c){
                        h4= (byte) j;
                        break;
                    }
                }
                if(h1==-1 || h2==-1 || h3==-1 || h4==-1){return null;}//如果没有找到对应字符，就返回空
                result[i/4]= (byte) ((h1<<6)|(h2<<4)|(h3<<2)|(h4));
            }
        }catch (Exception e){
            return null;
        }
        return result;
    }
}
