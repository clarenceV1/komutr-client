package com.komutr.client.been;

import java.io.Serializable;

public class Station implements Serializable {
    //[{"id":1,"latitude":"26.11395","longitude":"118.07404","name":"厦门站"}]
    private String id;
    private double latitude;
    private double longitude;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
