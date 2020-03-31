package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.impl.interfaces.KeyValueDatabase;
import com.zz.utils.threadsafe.storage.impl.util.DatabaseRegion;

import java.io.IOException;

public class DefaultValueDatabase implements KeyValueDatabase {
    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void set(String key, String value) {

    }

    public String get(String key, DatabaseRegion region) {
        return null;
    }

    public void set(String key, String value, DatabaseRegion region) {

    }

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
