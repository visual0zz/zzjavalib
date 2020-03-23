package com.zz.utils.threadunsafe.storage.impl;

import com.zz.utils.threadsafe.filesystem.Bash;
import com.zz.utils.threadunsafe.storage.DatabaseException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 将git仓库组织成一个键值数据库
 */
class GitRepoDatabase {
    private final int LIMITATION = 500;//限制一个key可以储存多少个字符
    private final Charset CHARSET=StandardCharsets.UTF_8;//储存使用的编码格式

    /**
     * 不参与同步的key，这个目录下的数据只限于本地
     */
    private final String GITIGNORE="/local/*";
    Git repo;
    File baseFolder;
    public GitRepoDatabase(File baseFolder){
        PrintStream ignore=null;
        try {
            Bash.mkdir(baseFolder);
            repo=Git.init().setDirectory(baseFolder).call();
            File gitignoreFile=new File(baseFolder.getAbsolutePath()+File.separator+".gitignore");
            if(!gitignoreFile.exists()){
                gitignoreFile.createNewFile();
                ignore=new PrintStream(new FileOutputStream(gitignoreFile),false,"utf-8");
                ignore.println(GITIGNORE);
                ignore.flush();
            }
        } catch (GitAPIException|IOException e) {
            throw new DatabaseException("GitRepoDatabase open repo failed.",e);
        }finally {
            if(ignore!=null)
                ignore.close();
        }
        this.baseFolder=baseFolder;
    }

    public void close() throws IOException {
        repo.close();
    }

    //region #private#
    private String get(String key) {
        try {
            return readFromFile(DatabaseKeyTool.keyToFilePath(key,baseFolder));
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
    }
    private void set(String key, String value) {
        try {
            writeToFile(DatabaseKeyTool.keyToFilePath(key,baseFolder),value);
        } catch (IOException e) {
            throw new DatabaseException(e);
        }
    }
    private void writeToFile(File file,String s) throws IOException {
        Bash.touch(file);
        FileOutputStream outputStream=new FileOutputStream(file);
        byte[] bytes=s.substring(0, Math.min(LIMITATION,s.length())).getBytes(CHARSET);
        outputStream.write(bytes);
    }
    private String readFromFile(File file) throws IOException {
        try {
            byte[]buffer=new byte[LIMITATION *4];
            FileInputStream inputStream=new FileInputStream(file);
            int count=inputStream.read(buffer);
            if(count==-1)return null;
            String result=new String(buffer,0,count,CHARSET);
            return result.substring(0,Math.min(LIMITATION,result.length()));
        } catch (FileNotFoundException e) {
            return null;
        }
    }
    //endregion
}
