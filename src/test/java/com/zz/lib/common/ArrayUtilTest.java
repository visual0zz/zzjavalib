package com.zz.lib.common;
import com.zz.lib.common.util.ArrayUtil;
import org.junit.Assert;
import org.junit.Test;

public class ArrayUtilTest {
    @Test
    public void testIsEmpty() {
        Integer[] arr1 = null;
        Integer[] arr2 = {};
        Integer[] arr3 = {1, 2, 3};
        Assert.assertTrue(ArrayUtil.isEmpty(arr1));
        Assert.assertTrue(ArrayUtil.isEmpty(arr2));
        Assert.assertFalse(ArrayUtil.isEmpty(arr3));
    }

    @Test
    public void testConcat() {
        Integer[] arr1 = {1, 2, 3};
        Integer[] arr2 = {4, 5, 6};
        String[] arr3 = {"a", "b", "c"};
        String[] arr4 = {"d", "e", "f"};
        Integer[] expected1 = {1, 2, 3, 4, 5, 6};
        String[] expected2 = {"a", "b", "c", "d", "e", "f"};
        Assert.assertArrayEquals(expected1, ArrayUtil.concat(arr1, arr2));
        Assert.assertArrayEquals(expected2, ArrayUtil.concat(arr3, arr4));
    }

    @Test
    public void testContainsNull() {
        Integer[] arr1 = {1, null, 3};
        Integer[] arr2 = {1, 2, 3};
        Assert.assertTrue(ArrayUtil.containsNull(arr1));
        Assert.assertFalse(ArrayUtil.containsNull(arr2));
    }

    @Test
    public void testIsAllNull() {
        Integer[] arr2 = {};
        Integer[] arr3 = {null, null, null};
        Integer[] arr4 = {null, 1, null};
        Assert.assertTrue(ArrayUtil.isAllNull(arr2));
        Assert.assertTrue(ArrayUtil.isAllNull(arr3));
        Assert.assertFalse(ArrayUtil.isAllNull(arr4));
    }

    @Test(expected = NullPointerException.class)
    public void testContainsNull2() {
        ArrayUtil.containsNull(null);
    }

    @Test(expected = NullPointerException.class)
    public void testIsAllNull2() {
        ArrayUtil.isAllNull(null);
    }

}
