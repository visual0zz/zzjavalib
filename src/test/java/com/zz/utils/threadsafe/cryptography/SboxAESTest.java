package com.zz.utils.threadsafe.cryptography;

import com.zz.lib.os.constant.SboxAES;
import org.junit.Assert;
import org.junit.Test;

public class SboxAESTest {
    short []Sbox= SboxAES.Sbox;
    short []Rbox=SboxAES.Rbox;
    @Test
    public void testSbox(){
        for(int i=0;i<256;i++){
            Assert.assertEquals(i,Rbox[Sbox[i]]);
            Assert.assertEquals(i,Sbox[Rbox[i]]);
        }
    }
}