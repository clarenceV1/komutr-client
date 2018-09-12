package com.komutr.client.been;

import java.io.Serializable;

public class StationDetail implements Serializable {
//    {
//               "id": 30,
//                "latitude": "24.63618",
//                "longitude": "118.07404",
//                "route_id": 11,
//                "station_id": "8",
//                "station_name": "厦门站"
//    }
    private int id;
    private double latitude;
    private double longitude;
    private int route_id;
    private int station_id;
    private String station_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public String getStation_name() {
        return station_name;
    }

    public void setStation_name(String station_name) {
        this.station_name = station_name;
    }
}
