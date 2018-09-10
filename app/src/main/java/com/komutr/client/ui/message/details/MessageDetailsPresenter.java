package com.komutr.client.ui.message.details;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.logger.Logger;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.R;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.Message;
import com.komutr.client.been.MessageDetails;
import com.komutr.client.been.RespondDO;
import com.komutr.client.ui.message.MessageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MessageDetailsPresenter extends AppBasePresenter<MessageView> {

    @Inject
    public MessageDetailsPresenter() {

    }

    @Override
    public void onAttached() {
    }

    public void requestMessageDetails(String id) {
        String auth_key = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "system.message");
        query.put("auth_key", auth_key);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if (respondDO.isStatus() && !StringUtils.isEmpty(respondDO.getData())) {
                            MessageDetails messageDetails = JSON.parseObject(respondDO.getData(), MessageDetails.class);
                            respondDO.setObject(messageDetails);
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
