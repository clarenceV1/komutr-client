package com.komutr.client.been;

import java.io.Serializable;

public class OrderDetailStation implements Serializable{
    //"station":{"beg_station":"厦门站","end_station":"福州站"}}
    private String beg_station;
    private String end_station;

    public String getBeg_station() {
        return beg_station;
    }

    public void setBeg_station(String beg_station) {
        this.beg_station = beg_station;
    }

    public String getEnd_station() {
        return end_station;
    }

    public void setEnd_station(String end_station) {
        this.end_station = end_station;
    }
}
