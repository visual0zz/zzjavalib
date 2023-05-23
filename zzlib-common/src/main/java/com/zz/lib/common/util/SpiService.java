package com.zz.lib.common.util;

public interface SpiService {
    String DEFAULT_MODULE="default";
    /**
     * 此spi服务的模块,不同模块的spi互相隔离
     * @return 模块名称
     */
    default String module(){
        return DEFAULT_MODULE;
    }
    /**
     * 此spi服务的优先级，如果有多个服务均符合特定业务主键，则按照优先级进行分配
     * @return 优先级
     */
    default SpiPriority priority(){
        return SpiPriority.NORMAL;
    }

    /**
     * 此spi是否适用此业务主键
     * @param bizKeys 业务主键列表
     * @return 是否适用
     */
    boolean suit(Object...bizKeys);
}
