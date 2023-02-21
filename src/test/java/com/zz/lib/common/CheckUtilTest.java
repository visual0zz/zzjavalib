package com.zz.lib.common;

import com.zz.lib.common.exception.DataContentException;
import com.zz.lib.common.exception.DataSizeException;
import com.zz.lib.common.exception.DataTypeException;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class CheckUtilTest {
    @Test
    public void assertTrue1() {
        CheckUtil.mustTrue(true);
    }
    @Test(expected = RuntimeException.class)
    public void assertTrue2() {
        CheckUtil.mustTrue(false);
    }
    @Test
    public void assertNotNull1() {
        CheckUtil.mustNotNull("");
        CheckUtil.mustNotNull("","");
        CheckUtil.mustNotNull(9L,1,2,3);
    }
    @Test(expected = NullPointerException.class)
    public void assertNotNull2() {
        CheckUtil.mustNotNull(null, (Object) null);
    }
    @Test
    public void assertIsA1() {
        CheckUtil.mustIsA("",String.class);
        CheckUtil.mustIsA("",Object.class);
    }
    @Test(expected = DataTypeException.class)
    public void assertIsA2() {
        CheckUtil.mustIsA("",Integer.class);
    }
    @Test
    public void assertSize1() {
        CheckUtil.mustMatchSize(new String[]{""},1,1);
        CheckUtil.mustMatchSize(new String[]{"","123"},2,2);
        CheckUtil.mustMatchSize(new ArrayList<String>(),0,0);
    }
    @Test(expected = DataSizeException.class)
    public void assertSize2() {
        CheckUtil.mustMatchSize(new String[]{""},2,2);
    }
    @Test(expected = DataTypeException.class)
    public void assertSize3() {
        CheckUtil.mustMatchSize(1,2,2);
    }
    public void a(int b){

    }
    @Test
    public void assertMethodParamTypes1() throws NoSuchMethodException {
        CheckUtil.mustWithParamTypes(CheckUtilTest.class.getMethod("assertMethodParamTypes1"));
        CheckUtil.mustWithParamTypes(CheckUtil.class.getMethod("mustWithParamTypes", Method.class, Class[].class), Method.class, Class[].class);
        CheckUtil.mustWithParamTypes(CheckUtilTest.class.getMethod("a", int.class), Integer.TYPE);
    }
    @Test(expected = DataTypeException.class)
    public void assertMethodParamTypes2() throws NoSuchMethodException {
        CheckUtil.mustWithParamTypes(CheckUtil.class.getMethod("mustWithParamTypes", Method.class, Class[].class),Integer.class,Boolean.class);
    }
    @Test
    public void assertEquals1(){
        CheckUtil.mustEquals(null,null);
        CheckUtil.mustEquals("123","123");
    }
    @Test(expected = DataContentException.class)
    public void assertEquals2(){
        CheckUtil.mustEquals("null",null);
    }
    @Test(expected = DataContentException.class)
    public void assertEquals3(){
        CheckUtil.mustEquals("123","456");
    }

    @Test
    public void assertRegex1(){
        CheckUtil.mustMatchRegex("[0-9]{3}","123","456","789");
    }
    @Test(expected = DataContentException.class)
    public void assertRegex2(){
        CheckUtil.mustMatchRegex("[0-9]{2}","123","456","789");
    }
    @Test(expected = DataContentException.class)
    public void assertRegex3(){
        CheckUtil.mustMatchRegex("[0-9]{3}",null,"456","789");
    }

    @Test(expected = NullPointerException.class)
    public void assertRegex4(){
        CheckUtil.mustMatchRegex(null,null,"456","789");
    }

}