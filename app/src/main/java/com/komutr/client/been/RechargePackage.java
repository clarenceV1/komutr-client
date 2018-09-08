package com.komutr.client.been;

import java.util.List;
import java.util.Map;

public class RechargePackage {
    //{"packge":[{"amount":"100.00","description":"重置抢","discount":"0.00","id":1,"type":"1"},
    // {"amount":"100.00","discount":"0.00","id":2,"type":"1"},
    // {"amount":"100.00","discount":"0.00","id":3,"type":"1"},
    // {"amount":"100.00","discount":"0.00","id":4,"type":"1"}],
    // "payment":{"1":"菲律宾在线充值模式1","2":"菲律宾在线充值模式1","3":"菲律宾在线充值模式1"}}
    private List<RechargePackageItem> packge;
    private Map<String,String> payment;

    public List<RechargePackageItem> getPackge() {
        return packge;
    }

    public void setPackge(List<RechargePackageItem> packge) {
        this.packge = packge;
    }

    public Map<String, String> getPayment() {
        return payment;
    }

    public void setPayment(Map<String, String> payment) {
        this.payment = payment;
    }
}
