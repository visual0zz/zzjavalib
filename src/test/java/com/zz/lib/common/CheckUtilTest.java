package com.zz.lib.common;

import com.sun.tools.javac.comp.Check;
import com.zz.lib.common.exception.DataContentException;
import com.zz.lib.common.exception.DataSizeException;
import com.zz.lib.common.exception.DataTypeException;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

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
        CheckUtil.assertNotNull(null,null);
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
        CheckUtil.assertSize(new String[]{""},1,1);
        CheckUtil.assertSize(new String[]{"","123"},2,2);
        CheckUtil.assertSize(new ArrayList<String>(),0,0);
    }
    @Test(expected = DataSizeException.class)
    public void assertSize2() {
        CheckUtil.assertSize(new String[]{""},2,2);
    }
    @Test(expected = DataTypeException.class)
    public void assertSize3() {
        CheckUtil.assertSize(1,2,2);
    }
    public void a(int b){

    }
    @Test
    public void assertMethodParamTypes1() throws NoSuchMethodException {
        CheckUtil.assertMethodParamTypes(CheckUtilTest.class.getMethod("assertMethodParamTypes1"));
        CheckUtil.assertMethodParamTypes(CheckUtil.class.getMethod("assertMethodParamTypes", Method.class, Class[].class), Method.class, Class[].class);
        CheckUtil.assertMethodParamTypes(CheckUtilTest.class.getMethod("a", int.class), Integer.TYPE);
    }
    @Test(expected = DataTypeException.class)
    public void assertMethodParamTypes2() throws NoSuchMethodException {
        CheckUtil.assertMethodParamTypes(CheckUtil.class.getMethod("assertMethodParamTypes", Method.class, Class[].class),Integer.class,Boolean.class);
    }
    @Test
    public void assertEquals1(){
        CheckUtil.assertEquals(null,null);
        CheckUtil.assertEquals("123","123");
    }
    @Test(expected = DataContentException.class)
    public void assertEquals2(){
        CheckUtil.assertEquals("null",null);
    }
    @Test(expected = DataContentException.class)
    public void assertEquals3(){
        CheckUtil.assertEquals("123","456");
    }

    @Test
    public void assertRegex1(){
        CheckUtil.assertRegex("[0-9]{3}","123","456","789");
    }
    @Test(expected = DataContentException.class)
    public void assertRegex2(){
        CheckUtil.assertRegex("[0-9]{2}","123","456","789");
    }
    @Test(expected = DataContentException.class)
    public void assertRegex3(){
        CheckUtil.assertRegex("[0-9]{3}",null,"456","789");
    }

}