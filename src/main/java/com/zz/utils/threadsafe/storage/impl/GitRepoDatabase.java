package com.zz.utils.threadsafe.storage.impl;

import com.zz.utils.threadsafe.filesystem.Bash;
import com.zz.utils.threadsafe.storage.exceptions.ClosedDatabaseException;
import com.zz.utils.threadsafe.storage.KeyValueDatabase;
import com.zz.utils.threadsafe.storage.exceptions.DatabaseIOException;
import com.zz.utils.threadsafe.storage.exceptions.InvalidDatabaseKeyException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 将git仓库组织成一个键值数据库,键的格式为 a.b.Key  值是字符串
 * 写一个null表示删除对应键 ，每个键存在于三个域，Global Local Temp
 * 关系类比git config 的管理方式 ， 每个键可以储存 {@link GitRepoDatabase#LIMITATION}个字符 以
 */
class GitRepoDatabase implements KeyValueDatabase {
    private final int LIMITATION = 500;//限制一个key可以储存多少个字符
    private final Charset CHARSET=StandardCharsets.UTF_8;//储存使用的编码格式
    private final ConcurrentHashMap<String ,String> temp=new ConcurrentHashMap<>();//储存temp域的数据
    private final DatabaseGitSyncManager manager;//git 同步管理器
    private boolean closed=false;//是否已经关闭
    private ReadWriteLock lock=new ReentrantReadWriteLock();//用于同步关闭状态的读写锁

    private final String GLOBAL_PREFIX="global.";//global域的键需要添加的前缀
    private final String LOCAL_PREFIX="local.";//local域的键需要添加的前缀
    private final String GITIGNORE="/local@/*";//git需要忽略local文件夹下面的文件


    Git repo;
    File baseFolder;
    public GitRepoDatabase(String baseFolder){this(new File(baseFolder));}

    /**
     *
     * @param baseFolder 数据库使用的根目录
     */
    public GitRepoDatabase(File baseFolder){
        PrintStream ignore=null;
        try {
            Bash.mkdir(baseFolder);
            repo=Git.init().setDirectory(baseFolder).setBare(false).call();
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
        manager=new DatabaseGitSyncManager(repo,this,lock);//用于处理数据库同步操作的对象
    }
    @Override
    public String get(String key){return get(key, DatabaseRegion.Auto);}
    @Override
    public void set(String key,String value){set(key,value, DatabaseRegion.Auto);}

    /**
     * 由于键值的读写都映射为了文件操作，而操作系统对文件是由自己的同步机制的，没有必要在进行同步，
     * 这个读写锁主要是为了防止多线程close后继续使用的问题
     * @param key 键
     * @param region 域 用于表示这条数据的管理方法
     * @see  DatabaseRegion
     */
    @Override
    public String get(String key, DatabaseRegion region){
        key=Optional.of(key).get();//过滤null值
        region= Optional.of(region).get();
        if(!DatabaseKeyTool.isKeyValid(key))throw new InvalidDatabaseKeyException();//验证key格式
        try {
            lock.readLock().lock();
            if(closed)throw new ClosedDatabaseException("database is already closed.");
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
        }finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 由于键值的读写都映射为了文件操作，而操作系统对文件是由自己的同步机制的，没有必要在进行同步，
     * 所以这里使用了读锁来写，这个读写锁主要是为了防止多线程close后继续使用的问题
     * @param key 键
     * @param value 值
     * @param region 域 用于表示这条数据的管理方法
     * @see  DatabaseRegion
     */
    @Override
    public void set(String key, String value, DatabaseRegion region){
        key=Optional.of(key).get();//过滤null值
        region= Optional.of(region).get();
        if(!DatabaseKeyTool.isKeyValid(key))throw new InvalidDatabaseKeyException();//验证key格式
        try {
            lock.readLock().lock();
            if(closed)throw new ClosedDatabaseException("database is already closed.");
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
        }finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 返回管理器 管理器专门管理和Git同步有关的事情
     * @return 本仓库对应的管理器
     */
    @Override
    public DatabaseGitSyncManager manager(){
        try {
            lock.readLock().lock();
            if(closed)throw new ClosedDatabaseException("database is already closed.");
            return manager;
        }finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 关闭数据库对象
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        try {
            lock.writeLock().lock();
            closed=true;
            repo.close();
            manager.close();
            temp.clear();
        }finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean isClosed() {
        try {
            lock.readLock().lock();
            return closed;
        }finally {
            lock.readLock().unlock();
        }
    }
    //region #private#

    /**
     * 从文件读取键值
     * @param key 要读取的键
     * @return 得到的值 只能读取前 {@link GitRepoDatabase#LIMITATION}个字符
     */
    private String _get_(String key) {
        try {
            return readFromFile(DatabaseKeyTool.keyToFilePath(key,baseFolder));
        } catch (IOException e) {
            throw new DatabaseIOException(e);
        }
    }

    /**
     * 向文件写入值
     * @param key 要写的键
     * @param value 写入的值 只有前 {@link GitRepoDatabase#LIMITATION}个字符会被写入
     */
    private void _set_(String key, String value) {
        try {
            writeToFile(DatabaseKeyTool.keyToFilePath(key,baseFolder),value);
        } catch (IOException e) {
            throw new DatabaseIOException(e);
        }
    }

    /**
     * 写文件函数  不会全写 ，只会写前 {@link GitRepoDatabase#LIMITATION} 个字符
     * @param file 要写入的文件
     * @param s 要写入的字符串 如果为null会删除对应文件
     * @throws IOException 文件操作异常
     */
    private void writeToFile(File file,String s) throws IOException {
        if(s==null){//尝试两次删除对应文件
            //System.out.println("尝试删除文件");
            if(file.exists()){
                if(!file.delete()){
                    //System.out.println("删除失败");
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
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 读文件函数 ，不会完全读，只会读取前 {@link GitRepoDatabase#LIMITATION}个字符
     * @param file 要读取的文件路径
     * @return 读文件得到的内容 读取编码由{@link GitRepoDatabase#CHARSET}变量指定
     * @throws IOException 文件操作异常
     */
    private String readFromFile(File file) throws IOException {
        FileInputStream inputStream=null;
        try {
            byte[]buffer=new byte[LIMITATION *4];
            inputStream=new FileInputStream(file);
            int count=inputStream.read(buffer);
            if(count==-1)return null;
            String result=new String(buffer,0,count,CHARSET);
            return result.substring(0,Math.min(LIMITATION,result.length()));
        } catch (FileNotFoundException e) {
            return null;
        }finally {
            if(inputStream!=null)inputStream.close();
        }
    }
    //endregion

}
