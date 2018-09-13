package com.komutr.client.been;

public class Position {


    /**
     * name : 3站
     * station_id : 23
     * longitude : 116.35362
     * latitude : 39.91209
     */

    boolean startPosition;

    private String name;
    private String station_id;
    private String longitude;
    private String latitude;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * 是否为常用站点
     */
    boolean frequentlyStation;


    public boolean isStartPosition() {
        return startPosition;
    }

    public void setStartPosition(boolean startPosition) {
        this.startPosition = startPosition;
    }

    public boolean isFrequentlyStation() {
        return frequentlyStation;
    }

    public void setFrequentlyStation(boolean frequentlyStation) {
        this.frequentlyStation = frequentlyStation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStation_id() {
        return station_id;
    }

    public void setStation_id(String station_id) {
        this.station_id = station_id;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
