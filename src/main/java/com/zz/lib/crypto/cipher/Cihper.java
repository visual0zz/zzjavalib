package com.zz.lib.crypto.cipher;

import com.zz.lib.crypto.encoder.Encoder;

public interface Cihper{
    byte[] encrypt(byte[] raw,byte[] key,byte[]salt);
    byte[] decrypt(byte[] secret,byte[]key);
}
