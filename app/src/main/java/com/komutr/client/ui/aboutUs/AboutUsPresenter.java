package com.komutr.client.ui.aboutUs;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.AboutUs;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.Constant;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class AboutUsPresenter extends AppBasePresenter<AboutUsView> {

    @Inject
    public AboutUsPresenter() {

    }

    @Override
    public void onAttached() {
    }

    public void requestContent() {
        String auth= userInfoDao.get().getAppAuth();
        Map<String, String> query = new HashMap<>();
        query.put("m", "system.aboutUs");
        query.put("auth_key", auth);
        query.put("type", "2");
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                      //  {"content":"dsfdsfsf","created_at":"0000-00-00 00:00:00","id":1,"title":"dcvdsfsf"}
                        if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                            AboutUs aboutUs = JSON.parseObject(respondDO.getData(), AboutUs.class);
                            respondDO.setObject(aboutUs);
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
