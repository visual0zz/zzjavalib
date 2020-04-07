package com.zz.utils.threadsafe.math.prime;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class PrimeGeneratorTest {

    @Test
    public void roughCheckIsPrime() {
        Assert.assertTrue(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(13)));
        Assert.assertTrue(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(17)));
        Assert.assertTrue(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(149)));
        Assert.assertTrue(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(971)));
        Assert.assertTrue(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(2)));
        Assert.assertTrue(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(3)));
        Assert.assertTrue(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(5)));
        Assert.assertTrue(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(7)));

        Assert.assertFalse(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(-1)));
        Assert.assertFalse(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(0)));
        Assert.assertFalse(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(1)));
        Assert.assertFalse(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(-123)));
        Assert.assertFalse(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(4)));
        Assert.assertFalse(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(9)));
        Assert.assertFalse(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(144)));
        Assert.assertFalse(PrimeGenerator.roughCheckIsPrime(BigInteger.valueOf(3678941)));
    }
}