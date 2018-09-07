package com.komutr.client.been;

import java.io.Serializable;

public class SearchRoutes implements Serializable {
    private int routeId;
    private int shiftId;
    private int begStation;
    private  int endStation;

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    public int getBegStation() {
        return begStation;
    }

    public void setBegStation(int begStation) {
        this.begStation = begStation;
    }

    public int getEndStation() {
        return endStation;
    }

    public void setEndStation(int endStation) {
        this.endStation = endStation;
    }
}
