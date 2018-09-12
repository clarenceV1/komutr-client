package com.komutr.client.been;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class User {
//  注册/登录   {"auth_key":"JCNnKH7Y6HCFFuKK","avatar":"","avatar_thum":"","id":19,"phone":"13779926287","username":"vcf"}

    //获取用户信息 {"app_auth_key":"JCNnKH7Y6HCFFuKK","avatar":"","avatar_thum":"","email":"763287516@qq.com","id":19,"phone":"13779926287","username":"vcf"}
    private long id;
    @Id
    private long userId;//数据库ID
    private String user_type;
    private String username;
    private String phone;
    private String sex;
    private String big_area;
    private String province;
    private String avatar;
    private String avatar_thum;
    private String auth_key;
    private String email;

    private String birthday;


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

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
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

    public String getBig_area() {
        return big_area;
    }

    public void setBig_area(String big_area) {
        this.big_area = big_area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
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

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }
}
