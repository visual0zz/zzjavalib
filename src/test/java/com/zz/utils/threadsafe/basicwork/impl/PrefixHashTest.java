package com.zz.utils.threadsafe.basicwork.impl;

import com.zz.utils.threadsafe.basicwork.ByteArrayUtils;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class PrefixHashTest {

    @Test
    public void hash() throws UnsupportedEncodingException {
        String[]data={
                "fdasfasdfasdfsafshdfghgfhsryer12345678322"
                ,"fdasfasdfa54f6sd54af684sd56f468sda4f8sda4"
                ,"fdasfasdfasdfsafshdfghgfhsryer7895413878d"
                ,"fdasfasdfasdfsafshdfghgfhsryer45613215789"
                ,"fsadffasdfsdafasdfasdgfasdfasdf1234567876"};
        for(String s:data){
            //System.out.println(s);
            //System.out.println(ByteArrayUtils.bytes2binaryString(new PrefixHash().hash(s.getBytes("utf-8"))));
        }
    }
}