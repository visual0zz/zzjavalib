package com.zz.lib.common.util;

import com.zz.lib.common.tool.PackageScanner;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 简化业务spi切点实现的工具。
 */
public class SpiUtil {
    private final static HashMap<String, List<SpiService>> serviceHolder;
    private final static List<SpiService> EMPTY_LIST=new ArrayList<>();
    static {
        serviceHolder=new HashMap<>();
        PackageScanner scanner=new PackageScanner().addPackage("");
        scanner.addListener(clazz -> {
            if(SpiService.class.isAssignableFrom(clazz)){
                SpiService service= (SpiService) clazz.getConstructor().newInstance();
                serviceHolder.computeIfAbsent(service.module(),e->new ArrayList<>()).add(service);
            }
        });
        scanner.scan();
    }
    /**
     *
     * @param serviceDefine 服务定义类
     * @param module
     * @param bizKeys
     * @return
     * @param <T>
     */
    public <T extends SpiService> T getService(Class<T> serviceDefine,String module,Object ... bizKeys){
        List<SpiService>services= serviceHolder.getOrDefault(module, EMPTY_LIST).stream()
                .filter(s -> serviceDefine.isAssignableFrom(s.getClass()))
                .filter(s -> s.suit(bizKeys))
                .sorted(Comparator.comparing(SpiService::priority))
                .collect(Collectors.toList());
        SpiPriority topPriority=services.get(0).priority();
        services=services.stream()
                .filter(s->s.priority()==topPriority)
                .collect(Collectors.toList());
        CheckUtil.mustMatchSize(services,0,1
                ,"多个spi服务实例符合条件,无法确定应该使用哪一个:"+services.stream()
                        .map(Object::getClass).map(Class::getCanonicalName).collect(Collectors.toList()));
        CheckUtil.mustMatchSize(services,1,1
                ,"找不到符合条件的服务。");
        return (T) services.get(0);
    }

    public <T extends SpiService> T getService(Class<T> serviceDefine,Object ... bizKeys){
        return getService(serviceDefine,SpiService.DEFAULT_MODULE,bizKeys);
    }
}
