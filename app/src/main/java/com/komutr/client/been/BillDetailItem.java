package com.komutr.client.been;

import java.io.Serializable;

public class BillDetailItem implements Serializable{
    //{"id":2,"name":"Scan QR code to buy tickets "}
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
