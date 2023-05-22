package com.zz.lib.common;

import com.zz.lib.common.util.StringUtil;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class StringUtilTest {

    @Test
    public void format() {
        assertEquals("123456,{,},{hhh", StringUtil.format("123{},{,},{{}",456,"hhh"));
        assertEquals("{}{",StringUtil.format("{{}{","}"));
        assertEquals("{",StringUtil.format("{"));
        assertEquals("{}{}",StringUtil.format("{}{}","{}{","}"));
        assertEquals("{}{",StringUtil.format("{}{}","{}{",null));


        HashMap<String,Object> map=new HashMap<>();
        map.put("a",123);
        map.put("b","9f0sadf");
        assertEquals("{c}123,9f0sadf{", StringUtil.format("{c}{a},{b}{", map));
    }
    @Test
    public void testToString(){
        assertEquals("null",StringUtil.toString(null));
        assertEquals("123",StringUtil.toString(123L));
        assertEquals("[]",StringUtil.toString(new char[]{}));
        assertEquals("[a]",StringUtil.toString(new char[]{'a'}));
        assertEquals("[123, 456]",StringUtil.toString(new int[]{123,456}));
        Object[]a=new Object[2];
        a[0]=a;
        a[1]=a;
        assertEquals("[<...>, <...>]",StringUtil.toString(a));
        assertEquals("ggg",StringUtil.toString("ggg"));
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
        assertEquals("myCompanyName",StringUtil.fromCamelCase("MyCompanyName").toLowerCamelCase());
        assertEquals("MyCompanyName",StringUtil.fromCamelCase("myCompanyName").toUpperCamelCase());
        assertEquals("my-company-name",StringUtil.fromCamelCase("myCompanyName").toKebabCase());
        assertEquals("my_company_name",StringUtil.fromCamelCase("myCompanyName").toSnakeCase());
        assertEquals("myCompanyName",StringUtil.fromSnakeOrKebab("my_company_name").toLowerCamelCase());
        assertEquals("myCompanyName",StringUtil.fromSnakeOrKebab("my-company-name").toLowerCamelCase());
        assertEquals("myCompanyName",StringUtil.fromSnakeOrKebab("my-company-name").toLowerCamelCase());

        assertEquals("my0Company123Name",StringUtil.fromSnakeOrKebab("my-0company-123name").toLowerCamelCase());
        assertEquals("my-a-b-c-012-name",StringUtil.fromCamelCase("myABC012Name").toKebabCase());
    }
}