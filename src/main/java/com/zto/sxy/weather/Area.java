package com.zto.sxy.weather;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class Area {
    private String province;

    private String city;

    List<Weather> weathers;
}
