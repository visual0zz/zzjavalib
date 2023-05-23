package com.zz.lib.container;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MultikeyMapTest {

    @Test
    public void get() {
        MultiKeyMap<String> multikeyMap=new MultiKeyMap<>();
        multikeyMap.put("123","1");
        Assert.assertEquals("123",multikeyMap.get("1"));
        assertNull(multikeyMap.get("2"));
        Assert.assertNull(multikeyMap.get("1","1"));
        Assert.assertNull(multikeyMap.get("1",0L));
        Assert.assertNull(multikeyMap.get());
        Assert.assertNull(multikeyMap.get(0L));
    }

    @Test
    public void put() {
        MultiKeyMap<String> multikeyMap=new MultiKeyMap<>();
        multikeyMap.put("123","123",0L,1,"1");
        Assert.assertEquals("123",multikeyMap.get("123",0L,1,"1"));
        assertNull(multikeyMap.get("2"));
        Assert.assertNull(multikeyMap.get("1","1"));
        Assert.assertNull(multikeyMap.get("1",0L));
    }
}