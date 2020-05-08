package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.impl.interfaces.KeyValueDatabase;
import com.zz.utils.threadsafe.storage.impl.interfaces.KeyValueRegionDatabase;
import com.zz.utils.threadsafe.storage.impl.util.DatabaseRegion;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubKVDBTest {

    @Test
    public void test() {
        GitRepoKVDB db=GitRepoKVDB.getInstance("test\\subDBTest",0);
        KeyValueDatabase subDB=SubKVDB.getInstance(db,"mmm");
        db.set("aaa","aaa");
        Assert.assertNull(subDB.get("aaa"));
        db.set("mmm.aaa","aaa");
        Assert.assertEquals("aaa",subDB.get("aaa"));

        KeyValueRegionDatabase subRegionDB=SubKVDB.getInstance(db,"fd");
        subRegionDB.set("aaa","aaa", DatabaseRegion.Temp);
        Assert.assertNull(db.get("fd.aaa",DatabaseRegion.Local));
        Assert.assertNull(db.get("fd.aaa",DatabaseRegion.Global));
        Assert.assertEquals("aaa",db.get("fd.aaa",DatabaseRegion.Temp));

    }


}