package com.komutr.client.been;

public class BillDetail {
    //{"amount":"40.00","bill_id":3,"chauffeur":{"name":"asdsasda","phone":"21111111"},"create_at":"2018-08-31 05:39:37","item":{"id":2,"name":"Scan QR code to buy tickets "},"order_number":"B16T1535708377R7","status":1,"type":"less","user":{"name":"dsfsdafsdfsad","phone":"211111101"}}

    private String amount;
    private String bill_id;
    private Chauffeur  chauffeur;
    private String create_at;
    private String order_number;
    private String status;
    private String type;
    private BillDetailUser user;
    private BillDetailItem item;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBill_id() {
        return bill_id;
    }

    public void setBill_id(String bill_id) {
        this.bill_id = bill_id;
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BillDetailUser getUser() {
        return user;
    }

    public void setUser(BillDetailUser user) {
        this.user = user;
    }

    public BillDetailItem getItem() {
        return item;
    }

    public void setItem(BillDetailItem item) {
        this.item = item;
    }
}
