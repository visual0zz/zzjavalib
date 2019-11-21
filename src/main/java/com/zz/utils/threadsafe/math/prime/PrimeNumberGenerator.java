package com.zz.utils.threadsafe.math.prime;

public class PrimeNumberGenerator{
    private PrimeNumberGenerator(){}
    private static PrimeNumberGenerator generator;
    public PrimeNumberGenerator getInstance(){//构造单例
        if(generator==null)
            generator=new PrimeNumberGenerator();
        return generator;
    }

}
