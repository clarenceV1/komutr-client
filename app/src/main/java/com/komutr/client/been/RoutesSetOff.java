package com.komutr.client.been;

import java.io.Serializable;

public class RoutesSetOff implements Serializable{
    private boolean is_setoff;
    private String time_left;

    public boolean isIs_setoff() {
        return is_setoff;
    }

    public void setIs_setoff(boolean is_setoff) {
        this.is_setoff = is_setoff;
    }

    public String getTime_left() {
        return time_left;
    }

    public void setTime_left(String time_left) {
        this.time_left = time_left;
    }
}
