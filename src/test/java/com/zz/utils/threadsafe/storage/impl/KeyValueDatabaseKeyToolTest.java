package com.zz.utils.threadsafe.storage.impl;

import org.junit.Assert;
import org.junit.Test;

public class KeyValueDatabaseKeyToolTest {

    @Test
    public void isKeyValid() {
        Assert.assertTrue(DatabaseKeyTool.isKeyValid("1_f.fasd.fsadf.ggg"));
        Assert.assertTrue(DatabaseKeyTool.isKeyValid("123"));
        Assert.assertTrue(DatabaseKeyTool.isKeyValid("_"));
        Assert.assertTrue(DatabaseKeyTool.isKeyValid("方法"));
        Assert.assertTrue(DatabaseKeyTool.isKeyValid("aAzZ_方法.发士大夫"));

        Assert.assertFalse(DatabaseKeyTool.isKeyValid("1..2"));
        Assert.assertFalse(DatabaseKeyTool.isKeyValid("."));
        Assert.assertFalse(DatabaseKeyTool.isKeyValid("/@13"));
        Assert.assertFalse(DatabaseKeyTool.isKeyValid(" "));
        Assert.assertFalse(DatabaseKeyTool.isKeyValid(" a.  s "));

        Assert.assertFalse(DatabaseKeyTool.isKeyValid("}"));
        Assert.assertFalse(DatabaseKeyTool.isKeyValid("["));
    }
}