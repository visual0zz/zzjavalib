package com.zz.utils.threadsafe.storage.impl.interfaces;

import com.zz.utils.threadsafe.storage.impl.DatabaseGitSyncManager;
import com.zz.utils.threadsafe.storage.impl.util.DatabaseRegion;

import java.io.Closeable;
import java.io.IOException;

public interface KeyValueDatabase {
    String get(String key);
    void set(String key,String value);
}
