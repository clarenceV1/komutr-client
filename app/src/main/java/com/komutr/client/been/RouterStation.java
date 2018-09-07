package com.komutr.client.been;

public class RouterStation {
    //"station":{"beg":{"id":8,"latitude":"24.63618","longitude":"118.07404","name":"厦门站"},"end":{"id":20,"latitude":"25.98584","longitude":"25.98584","name":"福州北站"}}
    private Station beg;
    private Station end;

    public Station getBeg() {
        return beg;
    }

    public void setBeg(Station beg) {
        this.beg = beg;
    }

    public Station getEnd() {
        return end;
    }

    public void setEnd(Station end) {
        this.end = end;
    }
}
