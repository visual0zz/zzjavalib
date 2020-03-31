package com.zz.utils.threadsafe.basicwork;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class ResourcePoolTest {

    Random random=new Random();
    ResourcePool<String> singleton = new ResourcePool<>(this::newInstance);
    ResourcePool<String> multi = new ResourcePool<>(this::newInstance,10);

    @Test
    public void getAny() {
        String tmp=singleton.getAny();;
        Assert.assertEquals(tmp,singleton.getAny());

        tmp= multi.getAny();
        int count=0;
        for(int i=0;i<=20;i++){
            if(tmp==multi.getAny()){count++;}
        }
        Assert.assertTrue(count<10);
    }

    @Test
    public void getWithKey() {
        Assert.assertEquals(singleton.getWithKey("123"),singleton.getWithKey("456"));
        Assert.assertEquals(multi.getWithKey("123"),multi.getWithKey("123"));
        Assert.assertNotEquals(multi.getWithKey("123"),multi.getWithKey("456"));
        Assert.assertNotEquals(multi.getWithKey("123"),multi.getWithKey("789"));
    }

    private String newInstance() {
        return String.valueOf(random.nextInt());
    }

}