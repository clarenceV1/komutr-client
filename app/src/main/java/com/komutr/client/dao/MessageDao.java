package com.komutr.client.dao;

import android.util.Log;

import com.cai.framework.dagger.ActivityScope;
import com.komutr.client.been.Message;
import com.komutr.client.been.Message_;
import com.komutr.client.ui.message.MessagePresenter;

import java.util.ArrayList;
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

    /**
     * @param offset 从第几条取
     * @param limit  取出条数
     * @return
     */
    public List<Message> getMessageList(long offset, long limit) {
        return msgBox.query().build().find(offset, limit);
    }

    public void deleteAll() {
        msgBox.removeAll();
    }

    public List<Message> addAll(List<Message> messageList, int start, int size) {

        List<Message> msgList = new ArrayList<>();
        if (messageList != null && messageList.size() > 0) {
            for (Message message : messageList) {
                Message msg = getMessageByStatus(message.getId());
                if (msg == null || msg.getId() != -1) {
                    if (msg == null) {
                        add(message);
                    }
                    msgList.add(msg == null ? message : msg);
                }
            }
//           msgBox.put(messageList);
        }else {
            msgList.addAll(getMessageList(start,size));
        }
        return msgList;
    }

    public void add(Message message) {
        if (message != null) {
            msgBox.put(message);
        }
    }


    /**
     * 更新状态
     */
    public void updateStatus(int id, int status) {
        Message msg = msgBox.query().equal(Message_.id, id).build().findFirst();
        if (msg != null && status != msg.getStatus()) {
            Log.i("logs", "msg1=" + msg);
            msg.setStatus(status);
            msgBox.put(msg);
            Log.i("logs", "msg2=" + msgBox.query().equal(Message_.id, id).build().findFirst());
        }
    }

    public void updateAll(List<Message> messageList) {
        if (messageList != null && messageList.size() > 0) {
            msgBox.removeAll();
            msgBox.put(messageList);
        }
    }

    public Message getMessageByStatus(int id) {
        return msgBox.query().equal(Message_.id, id).build().findFirst();
    }
}
