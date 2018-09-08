package com.komutr.client.been;

import java.io.Serializable;

public class BillDetailUser implements Serializable {
    //"user":{"name":"dsfsdafsdfsad","phone":"211111101"}
    private String name;
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
