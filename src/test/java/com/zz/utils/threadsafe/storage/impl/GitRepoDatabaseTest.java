package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.InvalidDatabaseKeyException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GitRepoDatabaseTest {
    GitRepoDatabase database=new GitRepoDatabase("test\\repository_for_test");
    @Before
    public void before(){
        database.set("全局","全局", DatabaseRegion.Global);
        database.set("局部","局部", DatabaseRegion.Local);
        database.set("临时","临时", DatabaseRegion.Temp);
    }
    @Test
    public void getAndSet() {
        Assert.assertEquals("全局",database.get("全局"));
        Assert.assertEquals("局部",database.get("局部"));
        Assert.assertEquals("临时",database.get("临时"));

        Assert.assertNull(database.get("全局", DatabaseRegion.Local));
        Assert.assertNull(database.get("全局", DatabaseRegion.Temp));

        Assert.assertNull(database.get("局部", DatabaseRegion.Global));
        Assert.assertNull(database.get("局部", DatabaseRegion.Temp));

        Assert.assertNull(database.get("临时", DatabaseRegion.Local));
        Assert.assertNull(database.get("临时", DatabaseRegion.Global));
    }

    @Test
    public void set() {

    }

    @Test
    public void manager() {
    }

    @Test
    public void close() {
    }

    @Test(expected = InvalidDatabaseKeyException.class)
    public void testException1(){ database.set("#.&.^","fdas"); }
    @Test(expected = InvalidDatabaseKeyException.class)
    public void testException2(){ database.get("#.&.^"); }
    @Test(expected = InvalidDatabaseKeyException.class)
    public void testException3(){database.get("()"); }
}