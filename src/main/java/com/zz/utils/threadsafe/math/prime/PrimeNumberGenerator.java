package com.zz.utils.threadsafe.math.prime;

import com.zz.utils.threadsafe.basicwork.Incomplete;

import java.math.BigInteger;
@Deprecated
@Incomplete
public class PrimeNumberGenerator{
    private PrimeNumberGenerator(){}
    private static PrimeNumberGenerator generator=new PrimeNumberGenerator();
    public PrimeNumberGenerator getInstance(){//构造单例
        return generator;
    }

    /**
     * 获得第index个质数 2是第一个质数，1定义为第0个质数
     * 即[0,1,2,3,4 , 5,6]=[1,2,3,5,7 , 11,13]
     * @param index
     * @return
     */
    public BigInteger getPrimeAt(BigInteger index){
        return null;
    }
}
