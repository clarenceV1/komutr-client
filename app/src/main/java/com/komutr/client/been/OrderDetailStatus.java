package com.komutr.client.been;

import java.io.Serializable;

public class OrderDetailStatus implements Serializable{
    //"status":{"code":1,"refund_rate":30}
    private String code;    //类型：Number  必有字段  备注：订单状态 1 已支付 2未支付 3 已经使用 4 已经退票
    private String refund_rate;  //类型：Number  必有字段  备注：退票需要扣费

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
