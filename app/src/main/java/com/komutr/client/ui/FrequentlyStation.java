package com.komutr.client.ui;


import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * 常用站点
 */
@Entity
public class FrequentlyStation {

    /**
     * id : 3
     * route_id : 11
     * user_id : 16
     * shift_id : 6
     * user_total : 14
     * beg_station : 厦门站
     * beg_station_id : 8
     * beg_longitude : 118.07404
     * beg_latitude : 24.63618
     * beg_geo_hash : ws7unufuhsc
     * end_station_id : 9
     * end_station : 泉州站
     * end_longitude : 118.56741
     * end_latitude : 24.97344
     * create_at : 2018-09-07 11:04:33
     * update_at : 2018-09-12 01:56:44
     */
    @Id
    private long frequentlyStationId;//数据库ID

    private int id;
    private int route_id;
    private int user_id;
    private int shift_id;
    private int user_total;
    private String beg_station;
    private String beg_station_id;
    private String beg_longitude;
    private String beg_latitude;
    private String beg_geo_hash;
    private String end_station_id;
    private String end_station;
    private String end_longitude;
    private String end_latitude;
    private String create_at;
    private String update_at;


    public long getFrequentlyStationId() {
        return frequentlyStationId;
    }

    public void setFrequentlyStationId(long frequentlyStationId) {
        this.frequentlyStationId = frequentlyStationId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getShift_id() {
        return shift_id;
    }

    public void setShift_id(int shift_id) {
        this.shift_id = shift_id;
    }

    public int getUser_total() {
        return user_total;
    }

    public void setUser_total(int user_total) {
        this.user_total = user_total;
    }

    public String getBeg_station() {
        return beg_station;
    }

    public void setBeg_station(String beg_station) {
        this.beg_station = beg_station;
    }

    public String getBeg_station_id() {
        return beg_station_id;
    }

    public void setBeg_station_id(String beg_station_id) {
        this.beg_station_id = beg_station_id;
    }

    public String getBeg_longitude() {
        return beg_longitude;
    }

    public void setBeg_longitude(String beg_longitude) {
        this.beg_longitude = beg_longitude;
    }

    public String getBeg_latitude() {
        return beg_latitude;
    }

    public void setBeg_latitude(String beg_latitude) {
        this.beg_latitude = beg_latitude;
    }

    public String getBeg_geo_hash() {
        return beg_geo_hash;
    }

    public void setBeg_geo_hash(String beg_geo_hash) {
        this.beg_geo_hash = beg_geo_hash;
    }

    public String getEnd_station_id() {
        return end_station_id;
    }

    public void setEnd_station_id(String end_station_id) {
        this.end_station_id = end_station_id;
    }

    public String getEnd_station() {
        return end_station;
    }

    public void setEnd_station(String end_station) {
        this.end_station = end_station;
    }

    public String getEnd_longitude() {
        return end_longitude;
    }

    public void setEnd_longitude(String end_longitude) {
        this.end_longitude = end_longitude;
    }

    public String getEnd_latitude() {
        return end_latitude;
    }

    public void setEnd_latitude(String end_latitude) {
        this.end_latitude = end_latitude;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }
}
