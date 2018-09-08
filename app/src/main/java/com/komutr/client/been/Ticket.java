package com.komutr.client.been;

import java.io.Serializable;

public class Ticket implements Serializable {
    //"ticket":{"discount":"0.00","price":"0.00"}
    private String discount;
    private String price;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
