package com.zto.sxy.weather;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FileUtil {
    public static void writeHtmlToFile(String body, String city) {
        String fileName = "wh-" + city + ".html";
        URL url = FileUtil.class.getClassLoader().getResource("wh");
        File file = new File(url.getFile() + File.separator + fileName);
        try {
            FileUtils.write(file, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readHtmlToFile(String city) {
        String fileName = "wh-" + city + ".html";
        URL url = FileUtil.class.getClassLoader().getResource("wh");
        File file = new File(url.getFile() + File.separator + fileName);
        if (!file.exists()) {
            return null;
        }
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeWeatherJson(String weather) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fileName = "weather-" + format.format(LocalDate.now()) + ".json";

        URL url = FileUtil.class.getClassLoader().getResource("weather");
        File file = new File(url.getFile() + File.separator + fileName);
        try {
            FileUtils.write(file, weather);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static List<Area> readWeatherJson() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fileName = "weather-" + format.format(LocalDate.now()) + ".json";

        URL url = FileUtil.class.getClassLoader().getResource("weather");
        File file = new File(url.getFile() + File.separator + fileName);
        try {
            String data = FileUtils.readFileToString(file);

            List<Area> areas = Optional.of(JSONObject.parseArray(data, Area.class)).orElse(Collections.emptyList());
            return areas;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
