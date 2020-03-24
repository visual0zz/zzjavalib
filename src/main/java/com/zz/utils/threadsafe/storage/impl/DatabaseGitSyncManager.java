package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.exceptions.ClosedDatabaseException;
import com.zz.utils.threadsafe.storage.KeyValueDatabase;
import org.eclipse.jgit.api.Git;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.locks.ReadWriteLock;

public final class DatabaseGitSyncManager  implements Closeable {
    private final Git repo;
    private final ReadWriteLock lock;//宿主数据库的锁，所有repo操作都应该用这个锁,因为宿主数据库会管理repo的关闭

    private final KeyValueDatabase database;
    public DatabaseGitSyncManager(Git repo, KeyValueDatabase database, ReadWriteLock lock){
        this.repo=repo;this.database=database;
        this.lock=lock;
    }

    private void checkDatabaseIsClosed(){if(database.isClosed())throw new ClosedDatabaseException("database is already closed."); }

    @Override
    public void close() throws IOException {
    }
}
