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
}