package com.zz.lib.container.tuple;


public final class Tuple2 <T1,T2> extends Tuple{
    public Tuple2(T1 v1,T2 v2) {
        super(v1,v2);
    }
    public T1 getV1(){
        return (T1)getVn(1);
    }
    public T2 getV2(){
        return (T2)getVn(2);
    }
    public T1 getLeft(){
        return getV1();
    }
    public T2 getRight(){
        return getV2();
    }
}