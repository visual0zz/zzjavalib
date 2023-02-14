package com.zz.lib.common.container.tuple;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class TupleTest {

    @Test
    public void of() {
        Tuple5 tuple=Tuple.of(1,2,3,4,"");
        Assert.assertEquals(1,tuple.getVn(1));
        Assert.assertEquals(2,tuple.getV2());
        Assert.assertEquals(3,tuple.clone().getVn(3));
    }
}