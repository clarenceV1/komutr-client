package com.komutr.client.ui.message;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.logger.Logger;
import com.komutr.client.R;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.Message;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.Constant;
import com.komutr.client.dao.MessageDao;
import com.komutr.client.dao.UserInfoDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MessagePresenter extends AppBasePresenter<MessageView> {

    @Inject
    public Lazy<MessageDao> messageDao;
    
    @Inject
    public MessagePresenter() {

    }

    @Override
    public void onAttached() {
    }

    public void requestMessage(int start, int size) {
        String auth_key = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "system.message");
        query.put("auth_key", auth_key);
        query.put("limit", start + "," + size);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                            List<Message> messageList = JSON.parseArray(respondDO.getData(), Message.class);
                            respondDO.setObject(messageList);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.callback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setMsg(GodBaseApplication.getAppContext().getString(R.string.request_failed));
                        mView.callback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
