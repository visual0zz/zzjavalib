package com.zz.lib.common.json;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class JsonStrParserTest {

    @Test
    public void nextString() {
        JsonStrParser parser=new JsonStrParser("'abc\\t\\n\\u1111'");
        parser.index=1;
        Assert.assertEquals("abc\t\n\u1111", parser.nextString('\''));
        Assert.assertEquals(parser.charSequence.length(),parser.index);
        Assert.assertTrue(parser.ended);
    }
    @Test
    public void nextObject(){
        JsonStrParser parser=new JsonStrParser("{\"a\":'b','c':\"d\"}");
        parser.index=1;
        Map<String,Object> obj=parser.nextObject();
        Assert.assertEquals(2,obj.size());
        Assert.assertEquals("b",obj.get("a"));
        Assert.assertEquals("d",obj.get("c"));
    }

    @Test
    public void nextArray(){
        JsonStrParser parser=new JsonStrParser("[\"a\",'123','ggg']");
        parser.index=1;
        List<Object> obj=parser.nextArray();
        Assert.assertEquals(3,obj.size());
        Assert.assertEquals("a",obj.get(0));
        Assert.assertEquals("123",obj.get(1));
        Assert.assertEquals("ggg",obj.get(2));
    }
    @Test
    public void nextValue(){
        Assert.assertEquals("abc",new JsonStrParser("'abc'").nextValue());
        Assert.assertEquals("abc",new JsonStrParser("\"abc\"").nextValue());
        Assert.assertEquals(1234567890000L,new JsonStrParser("1234567890000").nextValue());
        Assert.assertEquals(123,new JsonStrParser("123").nextValue());
    }
}