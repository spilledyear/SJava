package com.bruce.im;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by bruce on 2019/5/31 21:08
 */
@Setter
@Getter
@ToString
public class ServerInfo {
    private String sn;
    private int port;
    private String address;

    public ServerInfo(String sn, int port, String address) {
        this.sn = sn;
        this.port = port;
        this.address = address;
    }

}
