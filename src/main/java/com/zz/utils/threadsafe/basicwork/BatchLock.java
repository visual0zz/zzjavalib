package com.zz.utils.threadsafe.basicwork;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 批量锁<br/>
 * 应用场景为 少量线程(例如20个线程) 对 大量不同资源(例如十万个小文件)进行争用，
 * 为了保证任何单独一个资源不会被两个线程同时使用，如果每个资源建立一个锁，太过浪费
 * 所以{@link BatchLock}是通过哈希表来共享锁，时间换空间，
 * 对外提供的借口调用时间略大于单独锁的调用时间，但可以极大的减少锁的用量
 * <br/>
 * 冲突：对不同对象的访问因为用了同一个锁而被阻塞的事件<br/>
 * 在
 * 冲突概率 约等于 1/{@link BatchLock#RATE}<br/>
 *
 * 注意事项: 不应该在已经获得某个key的锁的情况下获取另一个key的锁，
 * 随机的映射会让锁的关系乱七八糟，很容易照成死锁，批量锁内的每个key应该单独使用。
 *
 */
public class BatchLock {

    private static final int RATE=10;//提供的锁总量=RATE * threads_num, 冲突概率≈1/RATE
    private ReentrantLock[] locks;//储存锁的数组
    private int[]count;

    /**
     * @param threads_num 预估的并发线程量
     */
    public BatchLock(int threads_num) {
        locks=new ReentrantLock[threads_num*RATE];//储存锁的数组
        for(int i=0;i<locks.length;i++){
            locks[i]=new ReentrantLock();
        }
        count=new int[threads_num*RATE];
    }

    /**
     * 对某个资源上锁
     * @param key 资源的标识符
     * @return 当前资源锁计数，会在通一个线程反复lock时累加
     */
    public int lock(String key){
        int hash=getHashCode(key);
        locks[hash].lock();
        count[hash]++;
        return count[hash];
    }
    /**
     * 对某个资源解锁
     * @param key 资源的标识符
     * @return 当前资源锁计数，会在通一个线程反复unlock时累减
     */
    public int unlock(String key){
        int hash=getHashCode(key);
        locks[hash].unlock();
        count[hash]--;
        return count[hash];
    }

    private int getHashCode(String key){//key到某个锁位置的哈希映射
        int hash=key.hashCode();
        hash=hash>0?hash:-hash;
        hash=hash%locks.length;
        return hash;
    }

}
