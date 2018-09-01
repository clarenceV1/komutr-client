package com.komutr.client.been;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class UserInfo {
    @Id
    private long id;
    private long user_id;
    private String app_auth;

    public UserInfo() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getApp_auth() {
        return app_auth;
    }

    public void setApp_auth(String app_auth) {
        this.app_auth = app_auth;
    }
}
