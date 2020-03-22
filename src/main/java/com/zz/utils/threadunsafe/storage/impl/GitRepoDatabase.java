package com.zz.utils.threadunsafe.storage.impl;

import com.zz.utils.threadunsafe.filesystem.Bash;
import com.zz.utils.threadunsafe.storage.Database;
import com.zz.utils.threadunsafe.storage.DatabaseException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystemException;
import java.sql.DatabaseMetaData;

class GitRepoDatabase implements Database {
    private final int VALUE_LENGTH_LIMITATION = 500;//限制一个key可以储存多少个字符
    private final Charset CHARSET=StandardCharsets.UTF_8;//储存使用的编码格式
    Git repo;
    File baseFolder;
    public GitRepoDatabase(File baseFolder){
        try {
            repo=Git.init().setDirectory(baseFolder).call();
        } catch (GitAPIException e) {
            throw new DatabaseException("GitRepoDatabase open repo failed.",e);
        }
        this.baseFolder=baseFolder;
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
    private void writeToFile(File file,String s) throws IOException {
        Bash.touch(file);
        FileOutputStream outputStream=new FileOutputStream(file);
        byte[] bytes=s.substring(0, Math.min(VALUE_LENGTH_LIMITATION,s.length())).getBytes(CHARSET);
        outputStream.write(bytes);
    }

    private String readFromFile(File file) throws IOException {
        try {
            byte[]buffer=new byte[VALUE_LENGTH_LIMITATION*4];
            FileInputStream inputStream=new FileInputStream(file);
            int count=inputStream.read(buffer);
            if(count==-1)return null;
            String result=new String(buffer,0,count,CHARSET);
            return result.substring(0,Math.min(VALUE_LENGTH_LIMITATION,result.length()));
        } catch (FileNotFoundException e) {
            return null;
        }
    }
}
