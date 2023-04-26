package com.zz.lib.filesystem;


import java.io.File;
import java.util.List;

public interface FileTreeAccessor {
    /**
     * @param layer_level 当前文件夹在迭代树上的层数 根文件夹为0
     * @param folder 当前文件夹
     * @return 如果返回true会继续深入此文件夹进行迭代，返回false不会继续迭代这个文件夹下面的内容。\
     * 不管返回true还是false，都会对当前这个文件夹本身调用travelFolder。
     */
    default boolean intoFolder(int layer_level, File folder)throws Exception{
        return true;
    }
    /**
     * @param layer_level 当前文件夹在迭代树上的层数 根文件夹为0
     * @param folder 当前文件夹
     * @param result 用于用户自定义功能的实现，迭代完成后会返回这个值，可以在travel代码中对其进行更改。
     */
    default void accessFolder(int layer_level, File folder, List<String> result)throws Exception{

    }

    /**
     * @param layer_level 当前文件在迭代树上的层数 根文件夹为0
     * @param file 当前文件
     * @param result 用于用户自定义功能的实现，迭代完成后会返回这个值，可以在travel代码中对其进行更改。
     */
    void accessFile(int layer_level, File file, List<String> result) throws Exception;
}
