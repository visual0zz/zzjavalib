package com.zz.utils.threadsafe.basicwork.impl;

/**
 * 前缀哈希，保持原数据一定前缀特性的哈希，如果两个串长度接近 并且拥有公共前缀，
 * 则哈希结果也拥有公共前缀，且前缀长度与原串前缀长度正相关
 *
 * 产生的哈希是弱哈希，单向性不好，仅仅用于保持访问的局部性原理，用作储存的key
 */
final class PrefixHash {
    private long seed;
    private final byte FACTOR = 113;
    private final byte ADDER = 97;
    private final int PREFIX_LENGTH = 32/8;//前缀部分的长度 单位是字节
    private final int RANDOM_LENGTH = 32/8;//随机部分的长度 单位是字节
    public PrefixHash(){
        this(0);
    }
    public PrefixHash(long seed){
        this.seed=seed;
    }
    public byte[] hash(byte [] data){
        data=preprocess(data);

        byte[] prefix=new byte[PREFIX_LENGTH];
        for (int i=0;i<PREFIX_LENGTH;i++)prefix[i]=data[i];

        int index=0;
        byte[]pick=new byte[PREFIX_LENGTH*8];
        for(int i=1;i<data.length;i*=2){//取出预处理之后的数组的1 2 4 8 16 这些位置的字节
            pick[index]=data[i];
            index++;
            if(index>=PREFIX_LENGTH*8)break;//储存满了就退出，不管后面更长的数组
        }
        index-=8;
        for(int i=1;index>=0 && PREFIX_LENGTH-i>=0;index-=8,i++){
            prefix[PREFIX_LENGTH-i]= (byte) (pick[index] & 0x80
                                |pick[index+1] &0x40
                                |pick[index+2] &0x20
                                |pick[index+3] &0x10
                                |pick[index+4] &0x08
                                |pick[index+5] &0x04
                                |pick[index+6] &0x02
                                |pick[index+7] &0x01);//每八个字节 每个字节取一位组装成一个字节
        }
        return prefix;
    }
    private byte[] preprocess(byte []data){//预处理原数组,产生一个尺寸不小于PREFIX_LENGTH的数组
        byte[]result=new byte[data.length+PREFIX_LENGTH];
        byte value= (byte) seed;
        int i;
        for(i=0;i<data.length;i++){//用线性同余法求一个于原数组同长度的哈希数组
            value^=data[i];
            value*= FACTOR;
            value+= ADDER;
            result[i]=value;
        }
        while (i<data.length+PREFIX_LENGTH){//后面填充一些字节，保证输出数组长度永远大于需要的前缀长度
            value*= FACTOR;
            value+= ADDER;
            result[i]=value;
            i++;
        }
        return result;
    }

}
