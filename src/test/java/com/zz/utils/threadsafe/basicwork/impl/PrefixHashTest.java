package com.zz.utils.threadsafe.basicwork.impl;

import com.zz.utils.threadsafe.basicwork.ByteArrayUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class PrefixHashTest {

    @Test
    public void hash() throws UnsupportedEncodingException {
        String[]data={"1234a","1234ab","1234abc","1234abcd","5678abcde"};
        for(String s:data){
            System.out.println(s);
            System.out.println(ByteArrayUtils.bytes2binaryString(new PrefixHash().hash(s.getBytes("utf-8"))));
        }
    }
}