package com.komutr.client.been;

public class chauffeur {
    //[{"route_id":11,"shift":[{"beg_time_int":"0800","chauffeur":{"chauffeur_id":1,"licence_plate":"2123465132456","phone":"21111111"},"end_time_int":"","id":4,"interval":1},{"beg_time_int":"1000","end_time_int":"","id":6,"interval":1}],"station":{"beg":{"id":8,"latitude":"24.63618","longitude":"118.07404","name":"厦门站"},"end":{"id":20,"latitude":"25.98584","longitude":"25.98584","name":"福州北站"}},"ticket":{"discount":"0.00","price":"0.00"}}]
    private int chauffeur_id;
    private String licence_plate;
    private String phone;

    public int getChauffeur_id() {
        return chauffeur_id;
    }

    public void setChauffeur_id(int chauffeur_id) {
        this.chauffeur_id = chauffeur_id;
    }

    public String getLicence_plate() {
        return licence_plate;
    }

    public void setLicence_plate(String licence_plate) {
        this.licence_plate = licence_plate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
