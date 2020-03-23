package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.exceptions.ClosedDatabaseException;
import com.zz.utils.threadsafe.storage.Database;
import org.eclipse.jgit.api.Git;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.locks.Lock;

public final class DatabaseGitSyncManager  implements Closeable {
    private final Git repo;
    private final Lock lock;//宿主数据库的读锁，所有repo操作都应该用这个锁,因为宿主数据库会管理repo的关闭

    private final Database database;
    public DatabaseGitSyncManager(Git repo, Database database, Lock lock){
        this.repo=repo;this.database=database;
        this.lock=lock;
    }

    private void checkDatabaseIsClosedAndGetLock(){
        lock.lock();
        if(database.isClosed())throw new ClosedDatabaseException("database is already closed.");
    }


    @Override
    public void close() throws IOException {
    }
}
