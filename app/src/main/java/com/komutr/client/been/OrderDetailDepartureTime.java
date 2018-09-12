package com.komutr.client.been;

import java.io.Serializable;

public class OrderDetailDepartureTime implements Serializable{
    //"departure_time":{"hour":"08","minute":"00","time_interval":1}
    private String hour;
    private String minute;
    private String time_interval;

    private String full;

    private String note;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getTime_interval() {
        return time_interval;
    }

    public void setTime_interval(String time_interval) {
        this.time_interval = time_interval;
    }


    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
