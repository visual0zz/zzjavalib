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
        Assert.assertEquals("{}{}",StringUtil.format("{}{}","{}{","}"));
        Assert.assertEquals("{}{",StringUtil.format("{}{}","{}{",null));
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
        Assert.assertEquals("[<...>, <...>]",StringUtil.toString(a));
        Assert.assertEquals("ggg",StringUtil.toString("ggg"));
    }
    @Test
    public void isBlankStr(){
        Assert.assertTrue(StringUtil.isBlankStr(null));
        Assert.assertTrue(StringUtil.isBlankStr(""));
        Assert.assertTrue(StringUtil.isBlankStr(" "));
        Assert.assertTrue(StringUtil.isBlankStr("\t"));
        Assert.assertTrue(StringUtil.isBlankStr("      \t\n"));
        Assert.assertFalse(StringUtil.isBlankStr("fasdf"));
    }
}