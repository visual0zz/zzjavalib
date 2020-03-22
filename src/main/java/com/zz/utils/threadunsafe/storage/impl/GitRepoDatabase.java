package com.zz.utils.threadunsafe.storage.impl;

import com.zz.utils.threadunsafe.storage.Database;
import org.eclipse.jgit.api.Git;

import java.io.File;
import java.io.IOException;
import java.sql.DatabaseMetaData;

class GitRepoDatabase implements Database {
    Git repo;
    public GitRepoDatabase(File baseFolder){
        Git.lsRemoteRepository().setRemote("");
    }
    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void set(String key, String value) {

    }

    @Override
    public void flush() {

    }

    @Override
    public void close() throws IOException {

    }
}
