package com.zz.utils.threadunsafe.command.impl;

import com.zz.utils.threadunsafe.command.exceptions.CommandClassReadException;
import com.zz.utils.threadunsafe.command.exceptions.CommandOptionException;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class PackageScanner
{


    boolean recursive = true;//是否递归搜索
    private List<String> packageNames = new ArrayList<String>();
    private Filter filter = null;//过滤器
    private Listener listener = null;//监听器

    public void setRecursive(boolean recursive) {this.recursive = recursive; }
    public void setFilter(Filter filter) {this.filter = filter; }
    public void setListener(Listener listener) {this.listener = listener; }

    public void addPackage(String packageName)
    {
        if (packageName == null || !packageName.matches("^[\\w]+(\\.[\\w]+)*$"))
        {
            throw new IllegalArgumentException("非法包名.");
        }
        this.packageNames.add(packageName);
    }


    public void scan() throws IOException, CommandOptionException, CommandClassReadException {
        for (String packageName : packageNames)
        {
            ScanAPackage(packageName);
        }
    }

    private void tigerListener(Class<?> clazz) throws CommandOptionException, CommandClassReadException {
        if (filter==null||filter.shouldAccept(clazz))
        {
            if(listener!=null)listener.travelClass(clazz);
        }
    }

    //扫描jar格式的类
    private void ScanAPackage(String packageName) throws IOException, CommandClassReadException, CommandOptionException {
        // 获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs = null;
        try
        {
            dirs = Thread.currentThread().getContextClassLoader()
                    .getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements())
            {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol))
                {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    scanPackageByFile(packageName, filePath);
                }
                else if ("jar".equals(protocol))
                {
                    JarFile jar = null;
                    try
                    {
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        Enumeration<JarEntry> entries = jar.entries();
                        while (entries.hasMoreElements())
                        {
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            if (name.charAt(0) == '/')
                            {
                                name = name.substring(1);
                            }
                            if (name.startsWith(packageDirName))
                            {
                                int idx = name.lastIndexOf('/');
                                if (idx != -1)
                                {
                                    packageName = name.substring(0, idx).replace('/',
                                            '.');
                                }
                                if ((idx != -1) || recursive)
                                {
                                    if (name.endsWith(".class") && !entry.isDirectory())
                                    {
                                        String className = name.substring(
                                                packageName.length() + 1,
                                                name.length() - 6);
                                        try
                                        {
                                            Class<?> clazz = Thread.currentThread()
                                                    .getContextClassLoader().loadClass(
                                                            packageName + '.' + className);
                                            tigerListener(clazz);
                                        }
                                        catch (ClassNotFoundException e)
                                        {
                                            System.err.println("加载类出错");
                                        }
                                    }
                                }
                            }
                        }
                    }
                    catch (IOException e) { throw new IOException("从jar包获取类信息出错",e); }
                }
            }
        }
        catch (IOException e) { throw new IOException("扫描包出错",e); }
    }

//扫描class文件格式的类
    private void scanPackageByFile(String packageName,
                                   String packagePath) throws CommandClassReadException, CommandOptionException {
        File dir = new File(packagePath);
        if (!dir.exists() || !dir.isDirectory())
        {
            System.err.println("包 " + packageName + " 是空的");
            return;
        }
        File[] dirfiles = dir.listFiles(file -> (recursive && file.isDirectory())
                || (file.getName().endsWith(".class")));
        for (File file : dirfiles)
        {
            if (file.isDirectory())
            {
                scanPackageByFile(packageName + "." + file.getName(),
                        file.getAbsolutePath());
            }
            else
            {
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try
                {
                    Class<?> clazz = Thread.currentThread().getContextClassLoader()
                            .loadClass(packageName + '.' + className);
                    tigerListener(clazz);
                }
                catch (ClassNotFoundException e)
                {
                    System.err.println("找不到类文件");
                    e.printStackTrace();
                }
            }
        }
    }


    public static interface Filter { public boolean shouldAccept(Class<?> clazz);}

    public static interface Listener { public void travelClass(Class<?> clazz) throws CommandOptionException, CommandClassReadException;}

}