package com.zz.utils.threadsafe.basicwork;

import com.zz.utils.threadsafe.encode.ZZBase16;
import org.junit.Assert;
import org.junit.Test;

public class ZZBase16Test {

    @Test
    public void encode() {
        byte[]data=HashService.md5.getRandomHash().getByteArray();
        String code= ZZBase16.encode(data);
        byte[] decode=ZZBase16.decode(code);
        Assert.assertArrayEquals(data,decode);
    }

}