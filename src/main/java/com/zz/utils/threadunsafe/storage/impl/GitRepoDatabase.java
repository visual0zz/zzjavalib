package com.zz.utils.threadunsafe.storage.impl;

import com.zz.utils.threadunsafe.storage.Database;
import com.zz.utils.threadunsafe.storage.DatabaseException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.sql.DatabaseMetaData;

class GitRepoDatabase implements Database {
    Git repo;
    public GitRepoDatabase(File baseFolder){
        try {
            repo=Git.init().setDirectory(baseFolder).call();
        } catch (GitAPIException e) {
            throw new DatabaseException("GitRepoDatabase open repo failed.",e);
        }
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
