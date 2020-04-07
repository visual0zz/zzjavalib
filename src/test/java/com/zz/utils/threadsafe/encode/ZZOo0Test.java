package com.zz.utils.threadsafe.encode;

import com.zz.utils.threadsafe.basicwork.HashService;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZZOo0Test {

    @Test
    public void encode() {
        byte[]data= HashService.md5.getRandomHash().getByteArray();
        String code= ZZOoO.encode(data);
        //System.out.println(code);
        byte[] decode=ZZOoO.decode(code);
        Assert.assertArrayEquals(data,decode);
    }


}