package com.zz.lib.json.impl;

import org.junit.Assert;
import org.junit.Test;

public class JavaObject2JsonStrTest {

    @Test
    public void toJson() {
//        String json="   [    {        \"name1\": \"Name\",        \"name2\": \"\\\"Name\\\"\",        " +
//                "\"data\":{            \"root\":[3,1,4,1,5],            \"dig\":[[false],[],[[[]," +
//                "[1,\"a\",true]],[]]]        }    },    {        \"number1\": 100,       " +
//                " \"number2\": -100,        \"number3\": 99.99,        \"number4\": 1e2,       " +
//                " \"number5\": -1E2    },    {        \"flag1\": true,        \"flag2\": false,       " +
//                " \"flag3\": null,    }]  ";
//        Object obj=JsonStr2JavaContainer.parse(json);
//        System.out.println(JavaObject2JsonStr.toJson(obj));
    }

    @Test
    public void constructString() {
        JavaObject2JsonStr builder=new JavaObject2JsonStr(false);
        Assert.assertEquals("\"abc\"",builder.constructString("abc"));
        builder=new JavaObject2JsonStr(true);
        Assert.assertEquals("\"abc\"",builder.constructString("abc"));
        builder=new JavaObject2JsonStr(true);
        Assert.assertEquals("\"abc\"",builder.constructString("abc"));
    }

    @Test
    public void constructArray() {
        JavaObject2JsonStr builder=new JavaObject2JsonStr(false);
        Assert.assertEquals("[1,2,3]",builder.construct(new int[]{1,2,3},1));
        builder=new JavaObject2JsonStr(true);
        Assert.assertEquals("[\n  1,\n  2,\n  3\n]",builder.construct(new int[]{1,2,3},1));
        builder=new JavaObject2JsonStr(true);
        Assert.assertEquals("[\n    1,\n    2,\n    3\n  ]",builder.construct(new int[]{1,2,3},2));
    }

}