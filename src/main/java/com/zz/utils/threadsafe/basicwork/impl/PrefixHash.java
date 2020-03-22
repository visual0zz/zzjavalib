package com.zz.utils.threadsafe.basicwork.impl;

import com.zz.utils.threadsafe.basicwork.ByteArrayUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    /**
     * 根据种子生成随机哈希
     * @param seed 种子会参与到哈希过程中，不同的种子会生成两套不同的前缀哈希
     *
     */
    public PrefixHash(long seed){
        this.seed=seed;
    }
    public byte[] hash(byte [] data){
        byte []processedData=preprocess(data);
        byte[]prefix=getPrefixPart(processedData);
        byte[]random= new byte[0];
        try {
            random = getRandomPart(data,processedData);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);//这个是不可能发生的
        }
        return ByteArrayUtils.concat(prefix,random);
    }
    private byte[] getPrefixPart(byte[] processedData){
        byte[] prefix=new byte[PREFIX_LENGTH];
        for (int i=0;i<PREFIX_LENGTH;i++)prefix[i]= processedData[i];

        int index=0;
        byte[]pick=new byte[PREFIX_LENGTH*8];
        for(int i = 1; PREFIX_LENGTH+i< processedData.length; i+=processedData.length/pick.length+1){//取出预处理之后的数组的1 2 4 8 16 这些位置的字节
            pick[index]= processedData[i];
            index++;
            if(index>=PREFIX_LENGTH*8)break;//储存满了就退出，不管后面更长的数组
        }
        index-=1;
        for(int i=0;index-i>=0;i++){//将pick里面的每一个字节映射到输出的每一位
            prefix[PREFIX_LENGTH-i/8-1]^=(0x01 & (pick[index-i]*FACTOR+ADDER))<<i%8;
        }
        return prefix;
    }
    private byte[] getRandomPart(byte[] data,byte []processedData) throws NoSuchAlgorithmException {
        MessageDigest messageDigest=MessageDigest.getInstance("md5");
        messageDigest.update(ByteArrayUtils.concat(data,processedData));
        byte[]result=messageDigest.digest();
        while (result.length<RANDOM_LENGTH) {//如果数据量不够
            messageDigest.update(ByteArrayUtils.concat(processedData,result));
            result=ByteArrayUtils.concat(result,messageDigest.digest());
        }
        byte[]pick=new byte[RANDOM_LENGTH];
        System.arraycopy(result, 0, pick, 0, RANDOM_LENGTH);
        return pick;
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
            value^= seed*i;
            value*= FACTOR;
            value+= ADDER;
            result[i]=value;
            i++;
        }
        return result;
    }

}
