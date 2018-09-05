package com.komutr.client.been;

public class HelpCenter {
    //[{"created_at":"0000-00-00 00:00:00","id":1,"title":"购票常见问题","user_type":2},{"created_at":"2018-08-29 15:20:53","id":6,"title":"二维码常见问题","user_type":2}]
    private String created_at;
    private int id;
    private String title;
    private int user_type;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }
}
