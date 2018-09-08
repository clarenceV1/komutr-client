package com.komutr.client.been;

import java.io.Serializable;

public class BuyTicket implements Serializable{
   // {"amount":40,"order_id":"65","qty":"1","ticket_record":29}
    private String  amount;
    private String  order_id;
    private  String qty;
    private int ticket_record;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public int getTicket_record() {
        return ticket_record;
    }

    public void setTicket_record(int ticket_record) {
        this.ticket_record = ticket_record;
    }
}
