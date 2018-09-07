package com.komutr.client.been;

import java.util.List;

public class Routes {
    //[{"route_id":11,
    // "shift":[{"beg_time_int":"0800","chauffeur":{"chauffeur_id":1,"licence_plate":"2123465132456","phone":"21111111"},"end_time_int":"","id":4,"interval":1},
    // {"beg_time_int":"1000","chauffeur":{"chauffeur_id":1,"licence_plate":"2123465132456","phone":"21111111"},"end_time_int":"","id":6,"interval":1}],
    // "station":{"beg":{"id":8,"latitude":"24.63618","longitude":"118.07404","name":"厦门站"},"end":{"id":20,"latitude":"25.98584","longitude":"25.98584","name":"福州北站"}},
    // "ticket":{"discount":"0.00","price":"0.00"}}]
    private int route_id;
    private List<RoutesShift> shift;
    private RouterStation station;
    private Ticket ticket;

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public List<RoutesShift> getShift() {
        return shift;
    }

    public void setShift(List<RoutesShift> shift) {
        this.shift = shift;
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
