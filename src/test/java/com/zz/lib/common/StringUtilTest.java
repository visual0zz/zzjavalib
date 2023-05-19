package com.zz.lib.common;

import com.zz.lib.common.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class StringUtilTest {

    @Test
    public void format() {
        Assert.assertEquals("123456,{,},{hhh", StringUtil.format("123{},{,},{{}",456,"hhh"));
        Assert.assertEquals("{}{",StringUtil.format("{{}{","}"));
        Assert.assertEquals("{",StringUtil.format("{"));
        Assert.assertEquals("{}{}",StringUtil.format("{}{}","{}{","}"));
        Assert.assertEquals("{}{",StringUtil.format("{}{}","{}{",null));


        HashMap<String,Object> map=new HashMap<>();
        map.put("a",123);
        map.put("b","9f0sadf");
        assertEquals("{c}123,9f0sadf{", StringUtil.format("{c}{a},{b}{", map));
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

    @Test
    public void caseSwitch(){
        Assert.assertEquals("myCompanyName",StringUtil.fromCamelCase("MyCompanyName").toLowerCamelCase());
        Assert.assertEquals("MyCompanyName",StringUtil.fromCamelCase("myCompanyName").toUpperCamelCase());
        Assert.assertEquals("my-company-name",StringUtil.fromCamelCase("myCompanyName").toKebabCase());
        Assert.assertEquals("my_company_name",StringUtil.fromCamelCase("myCompanyName").toSnakeCase());
        Assert.assertEquals("myCompanyName",StringUtil.fromSnakeOrKebab("my_company_name").toLowerCamelCase());
        Assert.assertEquals("myCompanyName",StringUtil.fromSnakeOrKebab("my-company-name").toLowerCamelCase());
        Assert.assertEquals("myCompanyName",StringUtil.fromSnakeOrKebab("my-company-name").toLowerCamelCase());

        Assert.assertEquals("my0Company123Name",StringUtil.fromSnakeOrKebab("my-0company-123name").toLowerCamelCase());
        Assert.assertEquals("my-a-b-c-012-name",StringUtil.fromCamelCase("myABC012Name").toKebabCase());
    }
}