package com.komutr.client.been;

import java.io.Serializable;

public class Chauffeur implements Serializable {
    //[{"route_id":11,"shift":[{"beg_time_int":"0800","chauffeur":{"chauffeur_id":1,"licence_plate":"2123465132456","phone":"21111111"},"end_time_int":"","id":4,"interval":1},{"beg_time_int":"1000","end_time_int":"","id":6,"interval":1}],"station":{"beg":{"id":8,"latitude":"24.63618","longitude":"118.07404","name":"厦门站"},"end":{"id":20,"latitude":"25.98584","longitude":"25.98584","name":"福州北站"}},"ticket":{"discount":"0.00","price":"0.00"}}]
    private int chauffeur_id;
    private String licence_plate;
    private String phone;
    private String avatar;
    private String avatar_thum;
    private String name;//账单详情才用
    private String username;//订单列表里用
    private String id;//订单列表里用

    private Integer review;


    public Integer getReview() {
        return review;
    }

    public void setReview(Integer review) {
        this.review = review;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getAvatar_thum() {
        return avatar_thum;
    }

    public void setAvatar_thum(String avatar_thum) {
        this.avatar_thum = avatar_thum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

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
