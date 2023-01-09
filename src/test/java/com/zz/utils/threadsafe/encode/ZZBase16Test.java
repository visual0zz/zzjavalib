package com.zz.utils.threadsafe.encode;

import com.zz.lib.os.utils.encode.ZZBase16;
import com.zz.utils.threadsafe.hash.HashService;
import org.junit.Assert;
import org.junit.Test;

public class ZZBase16Test {

    @Test
    public void encode() {
        byte[]data= HashService.md5.getRandomHash().getByteArray();
        String code= ZZBase16.encode(data);
        byte[] decode=ZZBase16.decode(code);
        Assert.assertArrayEquals(data,decode);
    }

}