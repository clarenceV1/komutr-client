package com.komutr.client.been;

public class BuySellTicketComment {
    //{"buy":{"content":"dsfdsfsf","created_at":"0000-00-00 00:00:00","id":1,"title":"dcvdsfsf"},"refund":{"content":"dsfdsfsf","created_at":"0000-00-00 00:00:00","id":1,"title":"dcvdsfsf"}}
    private String content;
    private String created_at;
    private String id;
    private String title;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
