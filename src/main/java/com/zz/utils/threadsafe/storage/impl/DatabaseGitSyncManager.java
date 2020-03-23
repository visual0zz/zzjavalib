package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.storage.Database;
import org.eclipse.jgit.api.Git;

import java.io.Closeable;
import java.io.IOException;

public class DatabaseGitSyncManager  implements Closeable {
    private final Git repo;
    private final Database database;
    public DatabaseGitSyncManager(Git repo,Database database){
        this.repo=repo;this.database=database;
    }

    @Override
    public void close() throws IOException {
    }
}
