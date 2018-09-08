package com.komutr.client.been;

import java.io.Serializable;

public class RoutesShift implements Serializable {
    //[{"route_id":11,"shift":[{"beg_time_int":"0800","chauffeur":{"chauffeur_id":1,"licence_plate":"2123465132456","phone":"21111111"},"end_time_int":"","id":4,"interval":1},{"beg_time_int":"0900","chauffeur":{"chauffeur_id":1,"licence_plate":"2123465132456","phone":"21111111"},"end_time_int":"","id":5,"interval":1},{"beg_time_int":"1000","chauffeur":{"chauffeur_id":1,"licence_plate":"2123465132456","phone":"21111111"},"end_time_int":"","id":6,"interval":1}],"station":{"beg":{"id":8,"latitude":"24.63618","longitude":"118.07404","name":"厦门站"},"end":{"id":20,"latitude":"25.98584","longitude":"25.98584","name":"福州北站"}},"ticket":{"discount":"0.00","price":"0.00"}}]
    private String beg_time_int;
    private Chauffeur chauffeur;
    private String end_time_int;
    private String id;
    private int interval;

    public String getBeg_time_int() {
        return beg_time_int;
    }

    public void setBeg_time_int(String beg_time_int) {
        this.beg_time_int = beg_time_int;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

    public String getEnd_time_int() {
        return end_time_int;
    }

    public void setEnd_time_int(String end_time_int) {
        this.end_time_int = end_time_int;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
}
