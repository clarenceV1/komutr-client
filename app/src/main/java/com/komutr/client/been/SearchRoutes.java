package com.komutr.client.been;

import java.io.Serializable;

public class SearchRoutes implements Serializable {
    private String route_id;
    private RouterStation station;
    private Ticket ticket;
    private RoutesShift routesShift;//班次

    public RoutesShift getRoutesShift() {
        return routesShift;
    }

    public void setRoutesShift(RoutesShift routesShift) {
        this.routesShift = routesShift;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public RouterStation getStation() {
        return station;
    }

    public void setStation(RouterStation station) {
        this.station = station;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
