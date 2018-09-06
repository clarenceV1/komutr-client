package com.komutr.client.been;

public class Wallet {
    //{"balance":"0.00","is_active":1,"outlay_amount":"0.00","recharge_total":"0.00"}
    private String balance;
    private int is_active;
    private String outlay_amount;
    private String recharge_total;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public int getIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getOutlay_amount() {
        return outlay_amount;
    }

    public void setOutlay_amount(String outlay_amount) {
        this.outlay_amount = outlay_amount;
    }

    public String getRecharge_total() {
        return recharge_total;
    }

    public void setRecharge_total(String recharge_total) {
        this.recharge_total = recharge_total;
    }
}
