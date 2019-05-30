package com.zto.sxy.weather;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class Weather {
    /**
     * 时间
     */
    private String date;

    /**
     * 星期几
     */
    private String dayOfWeek;

    /**
     * 天气
     */
    private String weatherForecast;

    /**
     * 气温
     */
    private String temperature;

    /**
     * 风向
     */
    private String windDirection;

    /**
     * 风力
     */
    private String windPower;
}
