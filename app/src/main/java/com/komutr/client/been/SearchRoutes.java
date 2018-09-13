package com.komutr.client.been;

import java.io.Serializable;

public class SearchRoutes implements Serializable {
    private String beg_time_int;
    private Chauffeur chauffeur;
    private String end_time_int;
    private String id;
    private String interval;
    private Routes route;


    public String getBeg_time_int() {
        return beg_time_int;
    }

    public void setBeg_time_int(String beg_time_int) {
        this.beg_time_int = beg_time_int;
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

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Routes getRoute() {
        return route;
    }

    public void setRoute(Routes route) {
        this.route = route;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }
}
