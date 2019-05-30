package com.zto.sxy.weather;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JSONUtil {
    private static Map<String, String> mappingCode = new HashMap<>(1000);

    private static Map<String, String> mappingCodeForRead = new HashMap<>(1000);

    static {
        mappingCodeForRead = readMappingFromFile();
    }

    public static List<Area> readAreas() {
        List<Area> list = new ArrayList<>(1000);
        JSONObject jsonObject = JSONUtil.readAreaJson();
        JSONArray provinces = jsonObject.getJSONArray("provinces");
        for (int i = 0; i < provinces.size(); i++) {
            JSONObject province = provinces.getJSONObject(i);
            String provinceName = province.getString("provinceName");

            JSONArray citys = province.getJSONArray("citys");
            if (citys.size() == 1) {
                list.add(Area.builder().province(provinceName).city(provinceName).build());
                continue;
            }

            for (int j = 0; j < citys.size(); j++) {
                JSONObject city = citys.getJSONObject(j);
                String cityName = city.getString("cityName");
                list.add(Area.builder().province(provinceName).city(cityName).build());
            }
        }
        return list;
    }


    private static JSONObject readAreaJson() {
        String fileName = "province.json";
        try {
            URL url = JSONUtil.class.getClassLoader().getResource(fileName);
            File file = new File(url.getFile());
            String content = FileUtils.readFileToString(file, "UTF-8");
            JSONObject jsonObject = JSONObject.parseObject(content);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void writeMappingToFile(List<Area> areas) {
        final String BASE_URL = "http://www.15tianqi.com/?action=Cha";

        areas.forEach(v -> HttpUtil.sendPost(BASE_URL, v.getCity()));

        URL fileUrl = FileUtil.class.getClassLoader().getResource("codeMapping.json");
        File file = new File(fileUrl.getFile());
        try {
            String body = JSONObject.toJSONString(mappingCode);
            FileUtils.write(file, body);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> readMappingFromFile() {
        URL fileUrl = FileUtil.class.getClassLoader().getResource("codeMapping.json");
        File file = new File(fileUrl.getFile());
        try {
            String body = FileUtils.readFileToString(file, "UTF-8");

            return JSONObject.parseObject(body, Map.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void putMappingCode(String k, String v) {
        mappingCode.put(k, v);
    }

    public static String getMappingCode(String k) {
        return mappingCodeForRead.get(k);
    }
}
