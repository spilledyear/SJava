package com.hand.sxy.utils;

import java.io.*;
import java.util.logging.Logger;

/**
 * Created by spilledyear on 2017/8/29.
 * <p>
 * 对一个目录下的所有文件 转换编码
 */
public class EncordConvert {

    private static final Logger LOG = Logger.getLogger("EncordConvert");

    private static String path = "D:\\Workspace\\Projects\\Java";

    private static String directory = "C:\\Users\\spilledyear\\Desktop";

    private static String codeBefore = "GB2312";

    private static String codeAfter = "UTF-8";


    public static void main(String[] args) {
        File dir = new File(path);
        File dirPath = new File(directory);

        convert(dir, dirPath, "^.*?\\.(txt|java|class|project|classpath|xml)$");
    }


    private static void convert(File dir, File dirPath, String regex) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (new File(dir, name).isDirectory())
                    return true;

                if (name.matches(regex))
                    return true;
                else
                    return false;
            }
        });


        for (File file : files) {
            LOG.warning(file.toString());
            //如果没有指定另存目录名，此为当前目录的绝对路径
            String pathAbsolute = null;
            //如果没有指定另存目录名，此为当前文件编码后的绝对路径
            File fileModify = null;
            //定义另存目录对象
            File createDir = null;
            //定义另存目录中的文件对象
            File createFile = null;
            //如果当前file对象是一个目录，再调用此方法，递归。
            if (file.isDirectory()) {
                //获取此目录绝对路径
                String path = file.getAbsolutePath();
                //截掉当前目录
                String path2 = path.substring(0, path.lastIndexOf("\\"));
                //获取到上级目录
                String path3 = path2.substring(path2.lastIndexOf("\\"));
                //封装成对象方便传递。
                File file2 = null;
                if (dirPath.getName() == null || dirPath.getName().trim() == "") {
                    convert(file, dirPath, regex);
                } else {
                    file2 = new File(dirPath, path3);
                    convert(file, file2, regex);
                }

            } else {//不是目录，直接转码
                try {
                    //读取目录下文件，并按指定编码读取。
                    bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()), codeBefore));
                    //如果另存目录为空，表示存放到当前目录。
                    if (dirPath.getName() == null || dirPath.getName() == "") {
                        //绝对路径名 如 D:\action\1.txt
                        pathAbsolute = file.getAbsolutePath();
                        //截取后的路径 如D:\action\
                        String path1 = pathAbsolute.substring(0, pathAbsolute.lastIndexOf(file.getName()));
                        //新路径 如 D:\action\11.txt
                        String pathModify = path1 + "1" + file.getName();
                        fileModify = new File(pathModify);
                        bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileModify), codeAfter));
                    } else {//否则，将转码后的文件写入指定的目录

                        String path = file.getAbsolutePath();
                        String fileName = file.getName();
                        //获取文件所在的绝对路径目录
                        String path2 = file.getAbsolutePath().substring(0, path.lastIndexOf(fileName) - 1);
                        //获取文件所在的上一级目录
                        String path3 = path2.substring(path2.lastIndexOf("\\"));

                        //创建另存目录
                        createDir = new File(dirPath, path3);
                        //这里是创建多级目录，防止条件转码时(例如后缀名为.java或.txt)，
                        //符合条件的文件太深，造成目录创建失败，导致IO写入异常。
                        createDir.mkdirs();
                        createFile = new File(createDir, fileName);
                        bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(createFile), codeAfter));
                    }
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        bufferedWriter.write(line);
                        bufferedWriter.newLine();
                    }
                    //我觉得这里写不写都一样，最终关闭流的时候也会刷新。
                    //如果写入while循环里会降低效率，每行都要刷新。
                    //因为那样写入磁盘的次数就增多了
                    bufferedWriter.flush();

                } catch (Exception e) {
                    if (createDir != null)
                        createDir.deleteOnExit();
                    if (createFile != null)
                        createFile.deleteOnExit();
                    throw new RuntimeException("读写错误" + e);
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();

                        } catch (IOException e) {
                            throw new RuntimeException("输入流关闭错误");
                        }
                    }
                    if (bufferedWriter != null) {
                        try {
                            bufferedWriter.close();
                            if (fileModify != null) {
                                //将原文件删除
                                file.delete();
                                //创建一个和原文件同名的File对象
                                File file3 = new File(pathAbsolute);
                                //将编码后的文件名改成原文件名
                                fileModify.renameTo(file3);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException("输出流关闭错误");
                        }
                    }
                }

            }
        }
    }

}
