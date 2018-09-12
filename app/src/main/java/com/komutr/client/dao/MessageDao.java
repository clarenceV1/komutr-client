package com.komutr.client.dao;

import com.cai.framework.dagger.ActivityScope;
import com.komutr.client.been.Message;
import com.komutr.client.been.Message_;
import com.komutr.client.ui.message.MessagePresenter;

import java.util.List;

import javax.inject.Inject;

import io.objectbox.Box;

@ActivityScope
public class MessageDao extends BaseDAO {
    Box<Message> msgBox;

    @Inject
    public MessageDao() {
        msgBox = boxStore.boxFor(Message.class);
    }

    public List<Message> getMessageList() {
        return msgBox.query().build().find();
    }

    public void deleteAll() {
        msgBox.removeAll();
    }

    public void addAll(List<Message> messageList) {
        if (messageList != null && messageList.size() > 0) {
            msgBox.put(messageList);
        }
    }

    public void add(Message message) {
        if (message != null) {
            msgBox.put(message);
        }
    }

    public void updateAll(List<Message> messageList) {
        if (messageList != null && messageList.size() > 0) {
            msgBox.removeAll();
            msgBox.put(messageList);
        }
    }

    public int getStatus(int id) {
        Message message = msgBox.query().equal(Message_.id, id).build().findFirst();
        if (message != null) {
            return message.getStatus();
        }
        return 0;
    }
}
