package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.filesystem.Bash;
import com.zz.utils.threadsafe.storage.DatabaseIOException;
import com.zz.utils.threadsafe.storage.InvalidDatabaseKeyException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 将git仓库组织成一个键值数据库
 */
class GitRepoDatabase {
    private final int LIMITATION = 500;//限制一个key可以储存多少个字符
    private final Charset CHARSET=StandardCharsets.UTF_8;//储存使用的编码格式
    private final ConcurrentHashMap<String ,String> temp=new ConcurrentHashMap<>();
    private final DatabaseGitSyncManager manager;
    /**
     * 不参与同步的key，这个目录下的数据只限于本地
     */
    private final String GLOBAL_PREFIX="global.";
    private final String LOCAL_PREFIX="local.";
    private final String GITIGNORE="/local/*";


    Git repo;
    File baseFolder;
    public GitRepoDatabase(String baseFolder){this(new File(baseFolder));}
    public GitRepoDatabase(File baseFolder){
        PrintStream ignore=null;
        try {
            Bash.mkdir(baseFolder);
            repo=Git.init().setDirectory(baseFolder).call();
            File gitignoreFile=new File(baseFolder.getAbsolutePath()+File.separator+".gitignore");
            if(!gitignoreFile.exists()){//如果没有 .gitignore 文件就新建一个默认的， 内容由GITIGNORE常量指定
                gitignoreFile.createNewFile();
                ignore=new PrintStream(new FileOutputStream(gitignoreFile),false,"utf-8");
                ignore.println(GITIGNORE);
                ignore.flush();
            }
        } catch (GitAPIException|IOException e) {
            throw new DatabaseIOException("GitRepoDatabase open repo failed.",e);
        }finally {
            if(ignore!=null)
                ignore.close();
        }
        this.baseFolder=baseFolder;//保存仓库根目录地址
        manager=new DatabaseGitSyncManager(repo);//用于处理数据库同步操作的对象
    }

    public String get(String key,Region region){
        key=Optional.of(key).get();//过滤null值
        region= Optional.of(region).get();
        if(!DatabaseKeyTool.isKeyValid(key))throw new InvalidDatabaseKeyException();//验证key格式
        switch (region){
            case Auto:
                String tmp;
                tmp=temp.get(key);//按照顺序依次取
                if(tmp==null){tmp=_get_(LOCAL_PREFIX+key);}
                if(tmp==null){tmp=_get_(GLOBAL_PREFIX+key);}
                return tmp;
            case Global:
                return _get_(GLOBAL_PREFIX+key);
            case Local:
                return _get_(LOCAL_PREFIX+key);
            case Temp:
                return temp.get(key);
            default:
                return null;
        }
    }
    public void set(String key,String value,Region region){
        key=Optional.of(key).get();//过滤null值
        region= Optional.of(region).get();
        if(!DatabaseKeyTool.isKeyValid(key))throw new InvalidDatabaseKeyException();//验证key格式
        switch (region){
            case Auto:
                if(temp.get(key)!=null){
                    if(value==null){//value为空表示删除对应键
                        temp.remove(key);
                    }else {
                        temp.put(key,value);
                    }
                }else if(_get_(LOCAL_PREFIX+key)!=null){
                    _set_(LOCAL_PREFIX+key,value);
                }else {
                    _set_(GLOBAL_PREFIX+key,value);
                }
                break;
            case Global:
                _set_(GLOBAL_PREFIX+key,value);
                break;
            case Local:
                _set_(LOCAL_PREFIX+key,value);
                break;
            case Temp:
                if(value==null){//value为空表示删除对应键
                    temp.remove(key);
                }else {
                    temp.put(key,value);
                }
                break;
        }
    }
    public DatabaseGitSyncManager manager(){return manager;}
    public void close() throws IOException {repo.close();}
    //region #private#
    private String _get_(String key) {
        try {
            return readFromFile(DatabaseKeyTool.keyToFilePath(key,baseFolder));
        } catch (IOException e) {
            throw new DatabaseIOException(e);
        }
    }
    private void _set_(String key, String value) {
        try {
            writeToFile(DatabaseKeyTool.keyToFilePath(key,baseFolder),value);
        } catch (IOException e) {
            throw new DatabaseIOException(e);
        }
    }
    private void writeToFile(File file,String s) throws IOException {
        if(s==null){//尝试两次删除对应文件
            if(file.exists()){
                if(!file.delete()){
                    try {
                        Thread.sleep(31);
                    }catch (InterruptedException ignored) {
                    }
                    if(file.exists()){file.delete();}
                }
            }
            return;
        }
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

    /**
     * 用于标记数据的管理方式，
     * Global域 -- 这个域的数据会上传到中央仓库，会和其他电脑交换
     * Local域 -- 这个域的数据仅限本地储存，不会和外界交换
     * Temp域 -- 这个域的数据仅仅存在于内存中，程序停止就会消失
     * Auto域 -- 不指定域时的默认行为，\
     * 当读 Auto域时 相当于依次读取Temp Local Global 任意一个域有对应值就返回，如果都没有就返回null
     * 当写Auto域时 相当于依次寻找Temp Local Global 任意一个域有对应值就修改那个值，如果都没有就修改Global域
     */
    public enum Region {Auto, Global, Local, Temp}
}
