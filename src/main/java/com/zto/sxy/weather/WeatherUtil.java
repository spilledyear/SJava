package com.zto.sxy.weather;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WeatherUtil {
    static final String BASE_URL = "http://www.15tianqi.com";

    public static void main(String[] args) {
        List<Area> areas = JSONUtil.readAreas();

        areas = areas.stream().map(v -> {
            String html = FileUtil.readHtmlToFile(v.getCity());
            if (StringUtils.isEmpty(html)) {
                html = requestHtml(v.getCity());
                FileUtil.writeHtmlToFile(html, v.getCity());
            }
            return parseHtml(v, html);
        }).collect(Collectors.toList());


        FileUtil.writeWeatherJson(JSONObject.toJSONString(areas));
        System.out.println(areas);
    }

    /**
     * get html by city code
     *
     * @param city
     * @return
     */
    public static String requestHtml(String city) {
        String url = BASE_URL + JSONUtil.getMappingCode(city);
        return HttpUtil.sendGet(url);
    }

    /**
     * parse the html body
     *
     * @param area
     * @param htmlBody
     * @return
     */
    private static Area parseHtml(Area area, String htmlBody) {
        String body = HtmlParse.removeHtmlTag(htmlBody);

        String city = area.getCity().replace("市", "");
        String start = "★" + city + "未来15天天气预报★";
        String end = city + "未来15天天气预报由15tianqi.com提供";
        try {
            body = body.substring(body.indexOf(start), body.indexOf(end));

            start = city + "天气预报";
            int charAt = body.indexOf(start);
            if (charAt == -1) {
                System.out.println("ERROR" + city);
                return area;
            }
            body = body.substring(body.indexOf(start));
        } catch (Exception e) {
            return area;
        }

        area.setWeathers(parseAreaWeather(start, body));
        if (area.getWeathers() == null || area.getWeathers().size() < 1) {
            System.out.println("WARN" + city);
        }
        return area;
    }

    private static List<Weather> parseAreaWeather(String prefix, String body) {
        List<Weather> weathers = new ArrayList<>(20);

        List<String> list = Arrays.asList(body.split("\r\n"));
        for (int i = 0; i < list.size(); i = i + 2) {
            String v = list.get(i);
            v = v == null ? "" : v.trim();
            if (StringUtils.isEmpty(v)) {
                continue;
            }

            v = v.trim().replace(prefix, "").replace(":", "");

            String date = v.split(" ")[0];
            String dayOfWeek = v.split(" ")[1];

            String v2 = list.get(i + 1);
            String[] items = v2.trim().replace("\n", "").split("，");

            String weatherForecast = items[0];
            int charAt = items[1].lastIndexOf("°C") + 2;
            String temperature = items[1].substring(0, charAt);
            String windDirection = items[1].substring(charAt);
            String windPower = items[2];

            weathers.add(Weather.builder()
                    .date(date)
                    .dayOfWeek(dayOfWeek)
                    .weatherForecast(weatherForecast)
                    .temperature(temperature)
                    .windDirection(windDirection)
                    .windPower(windPower)
                    .build());
        }
        return weathers;
    }
}



