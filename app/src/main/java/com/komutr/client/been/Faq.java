package com.komutr.client.been;

public class Faq {
    //[{"content":"11111111111111","created_at":"2018-08-29 15:05:20","id":11,"title":"1111111"},{"content":"aaaaaaaa","created_at":"2018-08-29 15:26:11","id":12,"title":"aaaaa"},{"content":"测试内容\r\n","created_at":"2018-09-05 15:05:21","id":14,"title":"测试"}]
    private String content;
    private int id;
    private String created_at;
    private String title;

    boolean show;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

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

}
