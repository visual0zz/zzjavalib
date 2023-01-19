package com.zz.lib.common;

import com.zz.lib.common.exception.DataSizeException;
import com.zz.lib.common.exception.DataTypeException;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CheckUtilTest {
    @Test
    public void assertTrue1() {
        CheckUtil.assertTrue(true);
    }
    @Test(expected = RuntimeException.class)
    public void assertTrue2() {
        CheckUtil.assertTrue(false);
    }
    @Test
    public void assertNotNull1() {
        CheckUtil.assertNotNull("");
    }
    @Test(expected = NullPointerException.class)
    public void assertNotNull2() {
        CheckUtil.assertNotNull(null);
    }
    @Test
    public void assertIsA1() {
        CheckUtil.assertIsA("",String.class);
        CheckUtil.assertIsA("",Object.class);
    }
    @Test(expected = DataTypeException.class)
    public void assertIsA2() {
        CheckUtil.assertIsA("",Integer.class);
    }
    @Test
    public void assertSize1() {
        CheckUtil.assertSize(new String[]{""},1);
        CheckUtil.assertSize(new String[]{"","123"},2);
        CheckUtil.assertSize(new ArrayList<String>(),0);
    }
    @Test(expected = DataSizeException.class)
    public void assertSize2() {
        CheckUtil.assertSize(new String[]{""},2);
    }
    @Test(expected = DataTypeException.class)
    public void assertSize3() {
        CheckUtil.assertSize(1,2);
    }
}