package com.komutr.client.been;

import java.io.Serializable;

public class Routes implements Serializable {
    // [{"route_id":11,"shift":[{"beg_time_int":"0800","chauffeur":{"avatar":"/uploads/20180828/a0e6fd10585aaf8cfbba86d7de86fcec_tt.jpg","avatar_thum":"/uploads/20180828/thumb/a0e6fd10585aaf8cfbba86d7de86fcec_tt.jpg","chauffeur_id":1,"licence_plate":"2123465132456","phone":"21111111"},"end_time_int":"","id":4,"interval":1},{"beg_time_int":"0900","chauffeur":{"avatar":"/uploads/20180828/a0e6fd10585aaf8cfbba86d7de86fcec_tt.jpg","avatar_thum":"/uploads/20180828/thumb/a0e6fd10585aaf8cfbba86d7de86fcec_tt.jpg","chauffeur_id":1,"licence_plate":"2123465132456","phone":"21111111"},"end_time_int":"","id":5,"interval":1},{"beg_time_int":"1000","chauffeur":{"avatar":"/uploads/20180828/a0e6fd10585aaf8cfbba86d7de86fcec_tt.jpg","avatar_thum":"/uploads/20180828/thumb/a0e6fd10585aaf8cfbba86d7de86fcec_tt.jpg","chauffeur_id":1,"licence_plate":"2123465132456","phone":"21111111"},"end_time_int":"","id":6,"interval":1}],"station":{"beg":{"id":8,"latitude":"24.63618","longitude":"118.07404","name":"厦门站"},"end":{"id":20,"latitude":"25.98584","longitude":"25.98584","name":"福州北站"}},"ticket":{"discount":"0.00","price":"0.00"}}]
    //[{"route_id":11,
    // "shift":[{"beg_time_int":"0800","chauffeur":{"chauffeur_id":1,"licence_plate":"2123465132456","phone":"21111111"},"end_time_int":"","id":4,"interval":1},
    // {"beg_time_int":"1000","chauffeur":{"chauffeur_id":1,"licence_plate":"2123465132456","phone":"21111111"},"end_time_int":"","id":6,"interval":1}],
    // "station":{"beg":{"id":8,"latitude":"24.63618","longitude":"118.07404","name":"厦门站"},"end":{"id":20,"latitude":"25.98584","longitude":"25.98584","name":"福州北站"}},
    // "ticket":{"discount":"0.00","price":"0.00"}}]
    private String route_id;
    private RouterStation station;
    private Ticket ticket;
    private String line_number;

    public String getLine_number() {
        return line_number;
    }

    public void setLine_number(String line_number) {
        this.line_number = line_number;
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
