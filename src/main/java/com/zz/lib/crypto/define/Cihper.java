package com.zz.lib.crypto.define;


public interface Cihper{
    byte[] encrypt(byte[] raw,byte[] key,byte[]salt);
    byte[] decrypt(byte[] secret,byte[]key);
}
