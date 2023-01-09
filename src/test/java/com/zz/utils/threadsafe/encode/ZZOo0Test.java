package com.zz.utils.threadsafe.encode;

import com.zz.lib.os.utils.encode.ZZOoO;
import com.zz.utils.threadsafe.hash.HashService;
import org.junit.Assert;
import org.junit.Test;

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