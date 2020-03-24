package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.KeyValueDatabase;

import java.io.IOException;

public class DefaultValueDatabase implements KeyValueDatabase {
    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void set(String key, String value) {

    }

    @Override
    public String get(String key, DatabaseRegion region) {
        return null;
    }

    @Override
    public void set(String key, String value, DatabaseRegion region) {

    }

    @Override
    public DatabaseGitSyncManager manager() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public boolean isClosed() {
        return false;
    }
}
