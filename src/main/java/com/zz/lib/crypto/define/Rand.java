package com.zz.lib.crypto.define;

public interface Rand {
    void setSeed(byte[]seed);
    byte[] getNext(int n);
}
