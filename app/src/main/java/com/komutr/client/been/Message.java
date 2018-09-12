package com.komutr.client.been;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Message {
    //auto_create 没用问过服务端
    // [{"auto_create":1,"content":"是不是全部用户","create_at":"2018-08-31 11:02:36","id":1,"title":"测试2"},{"auto_create":1,"content":"是不是bing","create_at":"2018-08-31 10:46:18","id":17,"title":"测试"},{"auto_create":1,"content":"是不是xixxx","create_at":"2018-08-31 10:46:44","id":18,"title":"测试"},{"auto_create":1,"content":"是不是发给所有司机","create_at":"2018-08-31 11:10:49","id":21,"title":"测试全体司机"},{"auto_create":1,"content":"是不是发给全部用户","create_at":"2018-08-31 11:13:22","id":22,"title":"测试"}]
    private int id;
    @Id
    private long msgId;//数据库ID
    private String content;
    private String create_at;
    private String title;

    public long getMsgId() {
        return msgId;
    }

    public void setMsgId(long msgId) {
        this.msgId = msgId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
