package com.zz.utils.threadsafe.math.prime;

import java.math.BigInteger;

public class PrimeGenerator {
    private static final BigInteger [] basicTable;
    private static final BigInteger zero=BigInteger.ZERO;
    static {
        basicTable=new BigInteger[PrimeTable.basicPrimeNumbers.length];
        for(int i=0;i<PrimeTable.basicPrimeNumbers.length;i++){
            basicTable[i]= BigInteger.valueOf(PrimeTable.basicPrimeNumbers[i]);
        }
    }
    static boolean roughCheckIsPrime(BigInteger number){
        if(number.compareTo(BigInteger.ONE)<=0)return false;//负数不处理，直接认为不是质数
        for(int i=0;i<basicTable.length;i++){
            if(number.compareTo(basicTable[i])<=0)break;//如果数字小于表中数字就跳出
            if(number.mod(basicTable[i]).equals(zero))return false;
        }
        return true;
    }
}
