package com.komutr.client.been;

import java.io.Serializable;

public class OrderDetailStatus implements Serializable{
    //"status":{"code":1,"refund_rate":30}
    private String code;
    private String refund_rate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRefund_rate() {
        return refund_rate;
    }

    public void setRefund_rate(String refund_rate) {
        this.refund_rate = refund_rate;
    }
}
