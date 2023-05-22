package com.zz.lib.common.tool;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author vis
 * @apiNote 采用后序遍历，一个文件夹的所有子文件和文件夹被travel一遍之后，这个文件夹本身才会被travel
 * 这个类本身只会抛出FileNotFoundException 抛出Exception只是转发FileTreeTraveler接口抛出的异常
 * <p>
 * 使用方法为定义一个实现这个接口的类的对象(也就是traveler)，然后在其中自定义自己的操作，
 * 最后使用FileTreeTravelerManager来应用这个traveler。
 * 每迭代到一个文件或文件夹，traveler都会被提供以下参数
 * <p>
 * >1. int layer_level 当前文件在整个迭代树上的深度，根文件夹
 * 被定义为0,根文件夹的子文件和子文件夹被定义为1,这些子文件夹的
 * 子文件的子文件夹被定义为2,以此类推
 * >2. File folder或者File file 目标文件或者文件夹的File对象
 * >3. List<String> result 一个一开始是空的字符串数组，用于
 * 帮助traveler在不同的迭代分支间传递信息，没有预定义功能，用于实现
 * traveler的自定义功能，这个对象最后会作为FileTreeTravelerManager.travel
 * 函数的返回值。
 */
public final class FileTreeTraverseManager {
    private File rootFolder;//储存迭代树的根目录

    /**
     * @param folder 迭代树的根目录，但也可以是一个文件，如果是一个文件，就只会调用traveler.travelFile一次。
     * @throws FileNotFoundException 如果根目录根本不存在就抛出异常
     */
    public FileTreeTraverseManager(String folder) throws FileNotFoundException {
        this(new File(folder));
    }

    /**
     * @param folder 迭代树的根目录，但也可以是一个文件，如果是一个文件，就只会调用traveler.travelFile一次。
     * @throws FileNotFoundException 如果根目录根本不存在就抛出异常
     */
    public FileTreeTraverseManager(File folder) throws FileNotFoundException {
        if (!folder.exists()) throw new FileNotFoundException("找不到这个文件:" + folder.getPath());
        rootFolder = folder;//保存文件夹
    }

    /**
     * @param traveler 用于处理每个被遍历到的文件夹和文件的处理器对象
     * @return 返回用于用户自定义功能的字符串列表，这个列表会在遍历过程中反复传给traveler，供其根据情况修改其内容。
     */
    public List<String> travel(FileTreeAccessor traveler) throws Exception {
        ArrayList<String> list = new ArrayList<>();
        travelOneLayer(rootFolder, 0, traveler, list);
        return list;
    }

    private void travelOneLayer(File file, int level_count, FileTreeAccessor traveler, List<String> list) throws Exception {
        if (file.exists()) {//如果文件存在
            if (file.isDirectory()) {//如果是文件夹
                if (traveler.intoFolder(level_count, file)) {
                    File[] files = file.listFiles();
                    if (files != null && files.length > 0) {
                        for (File f : files) {
                            travelOneLayer(f, level_count + 1, traveler, list);//如果返回true，继续迭代其下内容
                        }
                    }
                }
                traveler.accessFolder(level_count, file, list);
            } else if (file.isFile()) {//如果是文件
                traveler.accessFile(level_count, file, list);
            }
        }
    }

}
