package com.zz.lib.common.container.tuple;


public final class Tuple3 <T1,T2,T3> extends Tuple{
    public Tuple3(T1 v1,T2 v2,T3 v3) {
        super(v1,v2,v3);
    }
    public T1 getV1(){
        return (T1)getVn(1);
    }
    public T2 getV2(){
        return (T2)getVn(2);
    }
    public T3 getV3(){
        return (T3)getVn(3);
    }
}