package com.komutr.client.been;

public class PersonInfo {


    /**
     * id : 16
     * username : test1
     * phone : 2111111101
     * sex : m
     * big_area : {"id":1,"name":"Metropolitan Manila"}
     * province : {"id":1,"name":"lkjlk"}
     * city : null
     * avatar : http://car.xinjuhao.wang/uploads/20180912/a70eda7be0442c6b82db6b289af46b65.png
     * avatar_thum : http://car.xinjuhao.wang/uploads/20180912/a70eda7be0442c6b82db6b289af46b65.png
     * birthday : 2018-05-12
     * email : w@w.com
     * auth_key : czcwKxv92HnCj5mX
     */

    private int id;
    private String username;
    private String phone;
    private String sex;
    private AreaProvince big_area;
    private AreaProvince province;
    private Object city;
    private String avatar;
    private String avatar_thum;
    private String birthday;
    private String email;
    private String auth_key;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public AreaProvince getBig_area() {
        return big_area;
    }

    public void setBig_area(AreaProvince big_area) {
        this.big_area = big_area;
    }

    public AreaProvince getProvince() {
        return province;
    }

    public void setProvince(AreaProvince province) {
        this.province = province;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAvatar_thum() {
        return avatar_thum;
    }

    public void setAvatar_thum(String avatar_thum) {
        this.avatar_thum = avatar_thum;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }


}
