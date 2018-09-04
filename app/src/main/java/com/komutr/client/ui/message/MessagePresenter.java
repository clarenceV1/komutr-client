package com.komutr.client.ui.message;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.Constant;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MessagePresenter extends AppBasePresenter<MessageView> {

    @Inject
    public MessagePresenter() {

    }

    @Override
    public void onAttached() {
    }

    public void requestMessage() {
        Map<String, String> query = new HashMap<>();
        query.put("m", "customer.verification");
        query.put("auth_key", Constant.AUTH_KEY);
        query.put("type", "1");// 1 注册 2 找回密码 3 重置密码 4.重新绑定
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if(respondDO.isStatus()&&!TextUtils.isEmpty(respondDO.getData())){
                            PhoneCode phoneCode = JSON.parseObject(respondDO.getData(), PhoneCode.class);
                            respondDO.setObject(phoneCode);
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
                        respondDO.setFromCallBack(-1);
                        mView.callback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
