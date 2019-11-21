package com.zz.utils.threadsafe.math.prime;

import java.math.BigInteger;

class PrimeStorageBlock {
    BigInteger blockIndex;// 这个块储存[2^i,2^i+1)之间的所有质数
    public PrimeStorageBlock(int blockIndex){

    }
}
