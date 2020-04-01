package com.zz.utils.threadsafe.basicwork;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZZBase16Test {

    @Test
    public void encode() {
        byte[]data=HashService.md5.getRandomHash().getByteArray();
        String code=ZZBase16.encode(data);
        System.out.println(code);
        byte[] decode=ZZBase16.decode(code);
        Assert.assertArrayEquals(data,decode);
    }

}