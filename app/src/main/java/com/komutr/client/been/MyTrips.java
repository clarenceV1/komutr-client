package com.komutr.client.been;

public class MyTrips {
    //{"amount":"40.00",
    // "buy_time":"2018-09-08 11:06:15",
    // "chauffeur":{"avatar":"http://car.xinjuhao.wang/uploads/20180828/a0e6fd10585aaf8cfbba86d7de86fcec_tt.jpg",
    // "avatar_thum":"http://car.xinjuhao.wang/uploads/20180828/thumb/a0e6fd10585aaf8cfbba86d7de86fcec_tt.jpg",
    // "id":1,"licence_plate":"2123465132456",
    // "phone":"21111111",
    // "username":"asdsasda"},
    //
    // "departure_time":{"hour":"08","minute":"00","time_interval":1},
    // "order_id":"68",
    // "order_number":"B16T1536419175R6",
    // "qty":1,"review":[],"route_id":11,"shift_id":4,
    // "station":{"beg_station":"厦门站","end_station":"福州站"},
    // "status":{"code":1,"refund_rate":30}}

    private String amount;
    private String buy_time;
    private Chauffeur chauffeur;
    private String order_id;
    private String order_number;
    private String qty;
    private String route_id;
    private String shift_id;
    private OrderDetailStation station;
    private OrderDetailDepartureTime departure_time;
    private Integer status;

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }

    private Review review;



    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBuy_time() {
        return buy_time;
    }

    public void setBuy_time(String buy_time) {
        this.buy_time = buy_time;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    public String getShift_id() {
        return shift_id;
    }

    public void setShift_id(String shift_id) {
        this.shift_id = shift_id;
    }

    public OrderDetailStation getStation() {
        return station;
    }

    public void setStation(OrderDetailStation station) {
        this.station = station;
    }

    public OrderDetailDepartureTime getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(OrderDetailDepartureTime departure_time) {
        this.departure_time = departure_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
