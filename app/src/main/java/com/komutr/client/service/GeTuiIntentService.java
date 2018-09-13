package com.komutr.client.service;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.komutr.client.base.App;
import com.komutr.client.common.AppDataStore;

import javax.inject.Inject;

import dagger.Lazy;

/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class GeTuiIntentService extends GTIntentService {
    public static final String TAG2 = "getuipush";

    @Inject
    public Lazy<AppDataStore> dataStore;

    public GeTuiIntentService() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void onReceiveServicePid(Context context, int pid) {
        Log.e(TAG2, "onReceiveServicePid -> " + "pid = " + pid);
    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage msg) {
        Log.e(TAG2, "onReceiveMessageData -> " + msg.toString());
    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {//f8feb38f61635a14831b850dc0943ac9
        if (dataStore != null && !TextUtils.isEmpty(clientid)) {
            dataStore.get().setGeTuiPushClientId(clientid);
        }
        Log.e(TAG2, "onReceiveClientId -> " + "clientid = " + clientid);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean online) {
        Log.e(TAG2, "onReceiveOnlineState -> " + "online = " + online);
    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage cmdMessage) {
        Log.e(TAG2, "onReceiveCommandResult -> " + "cmdMessage = " + cmdMessage.toString());
    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage msg) {
        Log.e(TAG2, "onNotificationMessageArrived -> " + "msg = " + msg.toString());
    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage msg) {
        Log.e(TAG2, "onNotificationMessageClicked -> " + "msg = " + msg.toString());
    }
}
