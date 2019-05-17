package com.zto.sxy.utils;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileRename {
    private static final String baseDir = "E:\\Conclusion\\Video\\35326911";
    private static final String newBaseDir = "E:\\Conclusion\\Video\\2222222\\";

    private static int num = 1;

    public static void main(String[] args) {
        rename(baseDir);
    }


    private static void rename(String path) {
        File file = new File(path);
        String[] files = file.list();
        List<File> fileList = Stream.of(files).map(v -> new File(path + File.separator + v)).collect(Collectors.toList());
        fileList = fileList.stream().sorted((v1, v2) -> (int) (v1.lastModified() - v2.lastModified())).collect(Collectors.toList());
        for (File f : fileList) {
            if (f.isDirectory()) {
                rename(f.getPath());
            } else {
                if (!f.getName().equals("0.blv")) {
                    continue;
                }

                f.renameTo(new File(newBaseDir + String.format("%03d", num++) + ".mp4"));
            }
        }
    }
}
