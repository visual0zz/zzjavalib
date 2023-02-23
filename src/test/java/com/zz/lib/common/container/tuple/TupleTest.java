package com.zz.lib.common.container.tuple;

import com.zz.lib.container.tuple.Tuple;
import com.zz.lib.container.tuple.Tuple5;
import org.junit.Assert;
import org.junit.Test;


public class TupleTest {

    @Test
    public void of() {
        Tuple5 tuple= Tuple.of(1,2,3,4,"");
        Assert.assertEquals(1,tuple.getVn(1));
        Assert.assertEquals(2,tuple.getV2());
        Assert.assertEquals(3,tuple.clone().getVn(3));
        Assert.assertEquals(Tuple.of(1,2,3),Tuple.of(1,2,3));
        Assert.assertEquals(tuple,tuple.clone());
        Assert.assertTrue(Tuple.of(1,2,3).compareTo(Tuple.of(1,2,3))==0);
        Assert.assertTrue(Tuple.of(1,2,4).compareTo(Tuple.of(1,2,3))>0);
        Assert.assertTrue(Tuple.of(1,2,1).compareTo(Tuple.of(1,2,3))<0);
        Assert.assertTrue(Tuple.of(1,null).compareTo(Tuple.of(1,2))<0);
        Assert.assertTrue(Tuple.of(1,2).compareTo(Tuple.of(1,2,3))<0);
        Assert.assertTrue(Tuple.of(1,2).compareTo(Tuple.of(1,2,null))<0);
    }
}