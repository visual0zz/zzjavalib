package com.zz.lib.crypto.encode.constant;

import com.zz.lib.crypto.constant.SboxAES;
import org.junit.Assert;
import org.junit.Test;

public class SboxAESTest{

    @Test
    public void testReversibility(){
        for(short in=0;in<0xff;in++){
            Assert.assertEquals(in, SboxAES.Rbox[SboxAES.Sbox[in]]);
        }
    }
}