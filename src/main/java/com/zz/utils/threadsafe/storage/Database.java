package com.zz.utils.threadsafe.storage;

import com.zz.utils.threadsafe.storage.impl.DatabaseGitSyncManager;
import com.zz.utils.threadsafe.storage.impl.DatabaseRegion;

import java.io.Closeable;
import java.io.IOException;

public interface Database extends Closeable {
    String get(String key);
    void set(String key,String value);

    String get(String key, DatabaseRegion region);

    void set(String key, String value, DatabaseRegion region);

    DatabaseGitSyncManager manager();

    @Override
    void close() throws IOException;
}
