package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.exceptions.InvalidDatabaseKeyException;
import org.junit.Assert;
import org.junit.Test;

public class GitRepoDatabaseTest {
    GitRepoDatabase database=new GitRepoDatabase("test\\repository_for_test");

    @Test
    public void getAndSet() {
        database.set("全局","全局", DatabaseRegion.Global);
        database.set("局部","局部", DatabaseRegion.Local);
        database.set("临时","临时", DatabaseRegion.Temp);

        Assert.assertEquals("全局",database.get("全局"));
        Assert.assertEquals("局部",database.get("局部"));
        Assert.assertEquals("临时",database.get("临时"));

        database.set("全局","Global");
        database.set("局部","Local");
        database.set("临时","Temp");

        Assert.assertNotNull(database.get("全局",DatabaseRegion.Global));
        Assert.assertNull(database.get("全局", DatabaseRegion.Local));
        Assert.assertNull(database.get("全局", DatabaseRegion.Temp));

        Assert.assertNull(database.get("局部", DatabaseRegion.Global));
        Assert.assertNotNull(database.get("局部",DatabaseRegion.Local));
        Assert.assertNull(database.get("局部", DatabaseRegion.Temp));

        Assert.assertNull(database.get("临时", DatabaseRegion.Global));
        Assert.assertNull(database.get("临时", DatabaseRegion.Local));
        Assert.assertNotNull(database.get("临时",DatabaseRegion.Temp));

    }

    @Test
    public void remove() {
        database.set("全局","全局", DatabaseRegion.Global);
        database.set("局部","局部", DatabaseRegion.Local);
        database.set("临时","临时", DatabaseRegion.Temp);
        Assert.assertEquals("全局",database.get("全局"));
        Assert.assertEquals("局部",database.get("局部"));
        Assert.assertEquals("临时",database.get("临时"));
        database.set("全局",null);
        database.set("局部",null);
        database.set("临时",null);
        Assert.assertNull(database.get("全局"));
        Assert.assertNull(database.get("局部"));
        Assert.assertNull(database.get("临时"));
    }

    @Test
    public void sameName(){//允许包和节点同名
        database.set("com.zz","菲尼克斯");
        database.set("com","123456",DatabaseRegion.Temp);
    }
    @Test(expected = InvalidDatabaseKeyException.class)
    public void testException1(){ database.set("#.&.^","fdas"); }
    @Test(expected = InvalidDatabaseKeyException.class)
    public void testException2(){ database.get("#.&.^"); }
    @Test(expected = InvalidDatabaseKeyException.class)
    public void testException3(){database.get("()"); }
}