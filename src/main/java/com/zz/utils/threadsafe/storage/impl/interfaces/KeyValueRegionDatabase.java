package com.zz.utils.threadsafe.storage.impl.interfaces;

import com.zz.utils.threadsafe.storage.impl.util.DatabaseRegion;

public interface KeyValueRegionDatabase extends KeyValueDatabase {
    String get(String key, DatabaseRegion region);

    void set(String key, String value, DatabaseRegion region);
}
