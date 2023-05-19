package com.zz.lib.common;

import com.zz.lib.common.tool.SimpleParamExtractor;
import org.junit.Assert;
import org.junit.Test;

public class SimpleParamExtractorTest {

    @Test
    public void ignoreRedundant() {
        SimpleParamExtractor extractor=SimpleParamExtractor.builder()
                .optionalBoolean("aspect")
                .requiredParam(1)
                .allowExtraOptionalParam()
                .build();
        SimpleParamExtractor.ParamInfo paramInfo=extractor.extract("--aspect","fff","--apf=123","-aspf","k");
        Assert.assertEquals(Boolean.TRUE,paramInfo.getOpt("aspect"));
        Assert.assertEquals("fff",paramInfo.getReqs().get(0));
        Assert.assertEquals("k",paramInfo.getReqs().get(1));
        Assert.assertEquals(Boolean.TRUE,paramInfo.getOpt("a"));
        Assert.assertEquals("123",paramInfo.getOpt("apf"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void notIgnore() {
        SimpleParamExtractor extractor=SimpleParamExtractor.builder()
                .optionalBoolean("aspect")
                .requiredParam(1)
                .allowExtraOptionalParam(false)
                .build();
        SimpleParamExtractor.ParamInfo paramInfo=extractor.extract("--aspect","fff","--apf=123","-aspf");
        Assert.assertEquals(Boolean.TRUE,paramInfo.getOpt("aspect"));
        Assert.assertEquals("fff",paramInfo.getReqs().get(0));
        Assert.assertEquals(Boolean.TRUE,paramInfo.getOpt("a"));
        Assert.assertEquals("123",paramInfo.getOpt("apf"));
    }
}