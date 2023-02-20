package com.zz.lib.common;

import com.zz.lib.common.exception.DataSizeException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void format() {
        Assert.assertEquals("123456,{,},{hhh",StringUtil.format("123{},{,},{{}",456,"hhh"));
        Assert.assertEquals("{}{",StringUtil.format("{{}{","}"));
        Assert.assertEquals("{",StringUtil.format("{"));
    }
    @Test(expected = DataSizeException.class)
    public void formatError1() {
        StringUtil.format("123{},{,},{{}",456,"hhh","g");
    }
    @Test(expected = DataSizeException.class)
    public void formatError2() {
        Assert.assertEquals("123456,{,},{hhh",StringUtil.format("123{},{,},{{}",456));
    }
    @Test
    public void testToString(){
        Assert.assertEquals("null",StringUtil.toString(null));
        Assert.assertEquals("123",StringUtil.toString(123L));
        Assert.assertEquals("[]",StringUtil.toString(new char[]{}));
        Assert.assertEquals("[a]",StringUtil.toString(new char[]{'a'}));
        Assert.assertEquals("[123, 456]",StringUtil.toString(new int[]{123,456}));
        Object[]a=new Object[2];
        a[0]=a;
        a[1]=a;
        Assert.assertEquals("[[...], [...]]",StringUtil.toString(a));
        Assert.assertEquals("ggg",StringUtil.toString("ggg"));
    }
}