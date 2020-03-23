package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.InvalidDatabaseKeyException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GitRepoDatabaseTest {
    GitRepoDatabase database=new GitRepoDatabase("test\\repository_for_test");
    @Before
    public void before(){
        database.set("com.zz","abc", GitRepoDatabase.Region.Global);
        database.set("com.zz","abc", GitRepoDatabase.Region.Local);
        database.set("com.zz","abc", GitRepoDatabase.Region.Temp);
    }
    @Test
    public void getAndSet() {
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
    public void testException(){

    }
}