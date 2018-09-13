package com.komutr.client.been;

public class RoutesTime {
    private String beg_time;
    private String interval;
    private RoutesSetOff is_setoff;

    public String getBeg_time() {
        return beg_time;
    }

    public void setBeg_time(String beg_time) {
        this.beg_time = beg_time;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public RoutesSetOff getIs_setoff() {
        return is_setoff;
    }

    public void setIs_setoff(RoutesSetOff is_setoff) {
        this.is_setoff = is_setoff;
    }
}
