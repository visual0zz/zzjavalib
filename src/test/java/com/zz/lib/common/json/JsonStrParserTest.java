package com.zz.lib.common.json;

import org.junit.Assert;
import org.junit.Test;

public class JsonStrParserTest {

    @Test
    public void nextString() {
        JsonStrParser parser=new JsonStrParser();
        parser.charSequence="'abc\\t\\n\\u1111'";
        parser.index=1;
        Assert.assertEquals("abc\t\n\u1111", parser.nextString('\''));
        Assert.assertEquals(parser.charSequence.length(),parser.index);
        Assert.assertTrue(parser.ended);
    }
}