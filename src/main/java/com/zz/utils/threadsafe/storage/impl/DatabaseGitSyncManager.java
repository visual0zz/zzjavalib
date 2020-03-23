package com.zz.utils.threadsafe.storage.impl;

import org.eclipse.jgit.api.Git;

class DatabaseGitSyncManager {
    private final Git repo;
    public DatabaseGitSyncManager(Git repo){
        this.repo=repo;
    }
}
