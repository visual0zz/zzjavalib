package com.zz.lib.common.json.impl;

import com.zz.lib.common.json.impl.JavaObject2JsonStr;
import com.zz.lib.common.json.impl.JsonStr2JavaContainer;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class JavaThing2JsonStrTest {

    @Test
    public void toJson() {
        String json = "   [    {        \"name1\": \"Name\",        \"name2\": \"\\\"Name\\\"\",        " +
                "\"data\":{            \"root\":[3,1,4,1,5],            \"dig\":[[false],[],[[[]," +
                "[1,\"a\",true]],[]]]        }    },    {        \"number1\": 100,       " +
                " \"number2\": -100,        \"number3\": 99.99,        \"number4\": 1e2,       " +
                " \"number5\": -1E2    },    {        \"flag1\": true,        \"flag2\": false,       " +
                " \"flag3\": null,    }]  ";
        Object obj = JsonStr2JavaContainer.parse(json);
        Assert.assertEquals("[{\"data\": {\"dig\": [[false],[],[[[],[1,\"a\",true]],[]]],\"root\": [3,1,4,1,5]},\"name2\": \"\\\"Name\\\"\",\"name1\": \"Name\"},{\"number3\": 99.99,\"number4\": 100,\"number1\": 100,\"number2\": -100,\"number5\": -100},{\"flag2\": false,\"flag1\": true}]"
                , JavaObject2JsonStr.toJson(obj));
        Assert.assertEquals("[\n" +
                        "  {\n" +
                        "    \"data\": {\n" +
                        "      \"dig\": [\n" +
                        "        [\n" +
                        "          false\n" +
                        "        ],\n" +
                        "        [],\n" +
                        "        [\n" +
                        "          [\n" +
                        "            [],\n" +
                        "            [\n" +
                        "              1,\n" +
                        "              \"a\",\n" +
                        "              true\n" +
                        "            ]\n" +
                        "          ],\n" +
                        "          []\n" +
                        "        ]\n" +
                        "      ],\n" +
                        "      \"root\": [\n" +
                        "        3,\n" +
                        "        1,\n" +
                        "        4,\n" +
                        "        1,\n" +
                        "        5\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    \"name2\": \"\\\"Name\\\"\",\n" +
                        "    \"name1\": \"Name\"\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"number3\": 99.99,\n" +
                        "    \"number4\": 100,\n" +
                        "    \"number1\": 100,\n" +
                        "    \"number2\": -100,\n" +
                        "    \"number5\": -100\n" +
                        "  },\n" +
                        "  {\n" +
                        "    \"flag2\": false,\n" +
                        "    \"flag1\": true\n" +
                        "  }\n" +
                        "]"
                , JavaObject2JsonStr.toFormatJson(obj));
    }

    @Test
    public void constructString() {
        JavaObject2JsonStr builder = new JavaObject2JsonStr(false);
        Assert.assertEquals("\"abc\"", builder.constructString("abc"));
        builder = new JavaObject2JsonStr(true);
        Assert.assertEquals("\"abc\"", builder.constructString("abc"));
        builder = new JavaObject2JsonStr(true);
        Assert.assertEquals("\"abc\"", builder.constructString("abc"));
    }

    @Test
    public void constructArray() {
        JavaObject2JsonStr builder = new JavaObject2JsonStr(false);
        Assert.assertEquals("[1,2,3]", builder.construct(new int[]{1, 2, 3}, 1));
        builder = new JavaObject2JsonStr(true);
        Assert.assertEquals("[\n  1,\n  2,\n  3\n]", builder.construct(new int[]{1, 2, 3}, 1));
        builder = new JavaObject2JsonStr(true);
        Assert.assertEquals("[\n    1,\n    2,\n    3\n  ]", builder.construct(new int[]{1, 2, 3}, 2));
    }

    @Test
    public void constructNumber() {
        JavaObject2JsonStr builder = new JavaObject2JsonStr(false);
        Assert.assertEquals("10000", builder.construct(new BigDecimal("1e4"), 1));
        Assert.assertEquals("100E+6", builder.construct(new BigDecimal("1e8"), 2));
        Assert.assertEquals("10E+6", builder.construct(new BigDecimal("1e7"), 3));
        Assert.assertEquals("0.1", builder.construct(new BigDecimal("1e-1"), 3));
        Assert.assertEquals("0.2323", builder.construct(new BigDecimal("0.232300000"), 3));
        Assert.assertEquals("12E-9", builder.construct(new BigDecimal("1.2e-8"), 3));
        builder = new JavaObject2JsonStr(true);
        Assert.assertEquals("0", builder.construct(new BigDecimal("0"), 1));
        Assert.assertEquals("100E+6", builder.construct(new BigDecimal("1e8"), 2));
        Assert.assertEquals("10E+6", builder.construct(new BigDecimal("1e7"), 3));
        Assert.assertEquals("0.1", builder.construct(new BigDecimal("1e-1"), 3));
        Assert.assertEquals("0.2323", builder.construct(new BigDecimal("000.2323"), 3));
        Assert.assertEquals("12E-9", builder.construct(new BigDecimal("1.2e-8"), 3));

        Assert.assertEquals("123", builder.construct(123, 1));
        Assert.assertEquals("123", builder.construct(123L, 1));
        Assert.assertEquals("123", builder.construct(123.0, 1));
    }

    @Test
    public void testEscape() {
        Assert.assertEquals("\"\\u0001\\n反反复复\\\"'\\\\\\t\\r😊\"",JavaObject2JsonStr.toFormatJson("\u0001\n反反复复\"'\\\t\r😊"));
    }

}