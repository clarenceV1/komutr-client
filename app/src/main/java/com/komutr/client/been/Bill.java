package com.komutr.client.been;

public class Bill {
    //[{"created":"2018-08-31 05:39:37","id":3,"item":{"amount":"40.00","item_name":"Scan QR code to buy tickets ","type":"less"}},
    // {"created":"2018-08-31 05:39:47","id":4,"item":{"amount":"40.00","item_name":"Scan QR code to buy tickets ","type":"less"}},
    // {"created":"2018-08-31 05:39:54","id":5,"item":{"amount":"40.00","item_name":"Scan QR code to buy tickets ","type":"less"}},
    // {"created":"2018-08-31 05:39:59","id":6,"item":{"amount":"40.00","item_name":"Scan QR code to buy tickets ","type":"less"}},
    // {"created":"2018-08-31 05:40:05","id":7,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},
    // {"created":"2018-08-31 05:54:08","id":8,"item":{"amount":"0.00","item_name":"在线购票","type":"less"}},
    private BillItem item;
    private String created;
    private String id;

    public BillItem getItem() {
        return item;
    }

    public void setItem(BillItem item) {
        this.item = item;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
