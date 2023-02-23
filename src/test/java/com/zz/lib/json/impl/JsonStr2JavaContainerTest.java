package com.zz.lib.json.impl;

import com.zz.lib.json.JsonParseException;
import com.zz.lib.json.impl.JsonStr2JavaContainer;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class JsonStr2JavaContainerTest {

    @Test
    public void nextString() {
        JsonStr2JavaContainer parser=new JsonStr2JavaContainer("'abc\\t\\n\\u1111'");
        parser.index=1;
        Assert.assertEquals("abc\t\n\u1111", parser.parseString('\''));
        Assert.assertEquals(parser.charSequence.length(),parser.index);
        Assert.assertTrue(parser.ended);
    }
    @Test
    public void nextObject(){
        JsonStr2JavaContainer parser=new JsonStr2JavaContainer("{\"a\":'b','c':\"d\"}");
        parser.index=1;
        Map<String,Object> obj=parser.parseObject();
        Assert.assertEquals(2,obj.size());
        Assert.assertEquals("b",obj.get("a"));
        Assert.assertEquals("d",obj.get("c"));
    }

    @Test
    public void nextArray(){
        JsonStr2JavaContainer parser=new JsonStr2JavaContainer("[\"a\",'123','ggg']");
        parser.index=1;
        List<Object> obj=parser.parseArray();
        Assert.assertEquals(3,obj.size());
        Assert.assertEquals("a",obj.get(0));
        Assert.assertEquals("123",obj.get(1));
        Assert.assertEquals("ggg",obj.get(2));
    }
    @Test
    public void nextValue(){
        Assert.assertEquals("abc",new JsonStr2JavaContainer("'abc'").nextValue());
        Assert.assertEquals("abc",new JsonStr2JavaContainer("\"abc\"").nextValue());
        Assert.assertEquals(1234567890000L,new JsonStr2JavaContainer("1234567890000").nextValue());
        Assert.assertEquals(123,new JsonStr2JavaContainer("123").nextValue());
    }

    @Test
    public void parse(){
        String json="   [    {        \"name1\": \"Name\",        \"name2\": \"\\\"Name\\\"\",        " +
                "\"data\":{            \"root\":[3,1,4,1,5],            \"dig\":[[false],[],[[[]," +
                "[1,\"a\",true]],[]]]        }    },    {        \"number1\": 100,       " +
                " \"number2\": -100,        \"number3\": 99.99,        \"number4\": 1e2,       " +
                " \"number5\": -1E2    },    {        \"flag1\": true,        \"flag2\": false,       " +
                " \"flag3\": null,    }]  ";
        Assert.assertEquals("[{data={dig=[[false], [], [[[], [1, a, true]], []" +
                "]], root=[3, 1, 4, 1, 5]}, name2=\"Name\", name1=Name}, {number3=99" +
                ".99, number4=1E+2, number1=100, number2=-100, number5=-1E+2}, {flag2=f" +
                "alse, flag1=true}]",JsonStr2JavaContainer.parse(json).toString());
    }
    @Test(expected= JsonParseException.class)
    public void parseError(){
        JsonStr2JavaContainer.parse("'a':false");
    }
}