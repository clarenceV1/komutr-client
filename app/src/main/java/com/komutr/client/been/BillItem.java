package com.komutr.client.been;

public class BillItem {
    //"item":{"amount":"0.00","item_name":"在线购票","type":"less"}
    private String amount;
    private String item_name;
    private String type;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
