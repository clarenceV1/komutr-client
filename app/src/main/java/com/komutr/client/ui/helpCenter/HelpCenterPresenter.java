package com.komutr.client.ui.helpCenter;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.HelpCenter;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class HelpCenterPresenter extends AppBasePresenter<HelpCenterView> {

    @Inject
    public HelpCenterPresenter() {

    }

    @Override
    public void onAttached() {
    }

    /**
     *
     *
     */
    public void requestHelpList() {
        Map<String, Object> query = new HashMap<>();
        query.put("m", "system.faqItem");
        query.put("auth_key", Constant.AUTH_KEY);
        query.put("type", "2");
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        //[{"created_at":"0000-00-00 00:00:00","id":1,"title":"购票常见问题","user_type":2},{"created_at":"2018-08-29 15:20:53","id":6,"title":"二维码常见问题","user_type":2}]
                        if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                            List<HelpCenter> helpCenters = JSON.parseArray(respondDO.getData(), HelpCenter.class);
                            respondDO.setObject(helpCenters);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.helpListCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        mView.helpListCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
