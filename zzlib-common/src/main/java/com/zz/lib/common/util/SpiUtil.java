package com.zz.lib.common.util;

import com.zz.lib.common.tool.PackageScanner;

import java.util.HashMap;

/**
 * 简化spi实现的工具。
 */
public class SpiUtil {
    private final static HashMap<String,SpiService> serviceHolder;
    static {
        serviceHolder=new HashMap<>();
        PackageScanner scanner=new PackageScanner();
    }
    /**
     *
     * @param serviceDefine
     * @param module
     * @param bizKeys
     * @return
     * @param <T>
     */
    <T extends SpiService> T getService(Class<T> serviceDefine,String module,Object ... bizKeys){
        return null;
    }
}
