package com.hand.sxy.utils;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Created by spilledyear on 2017/8/29.
 *
 * 删除文件
 */
public class FileDelete {
    private static String path = "D:\\Workspace\\Projects\\Java";

    public static void main(String[] args){
        File dir = new File(path);

        delete(dir, "^.*?\\.(class)$");

    }

    private static void delete(File dir, String regex){

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


        for(File file : files){
            if(file.isDirectory()) {
                delete(file, regex);

            }else{

                file.delete();
            }
        }

    }
}
