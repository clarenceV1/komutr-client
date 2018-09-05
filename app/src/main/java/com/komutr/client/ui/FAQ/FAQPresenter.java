package com.komutr.client.ui.FAQ;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.Faq;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class FAQPresenter extends AppBasePresenter<FAQView> {

    @Inject
    public FAQPresenter() {

    }

    @Override
    public void onAttached() {
    }
    /**
     *
     */
    public void requestDetail(int contentType) {
        Map<String, String> query = new HashMap<>();
        query.put("m", "system.faq");
        query.put("auth_key", Constant.AUTH_KEY);
        query.put("user_type", "2");
        query.put("content_type", contentType + "");
        query.put("limit", "0,10");
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        //[{"content":"11111111111111","created_at":"2018-08-29 15:05:20","id":11,"title":"1111111"},{"content":"aaaaaaaa","created_at":"2018-08-29 15:26:11","id":12,"title":"aaaaa"},{"content":"测试内容\r\n","created_at":"2018-09-05 15:05:21","id":14,"title":"测试"}]
                        if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                            List<Faq> helpCenters = JSON.parseArray(respondDO.getData(), Faq.class);
                            respondDO.setObject(helpCenters);
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
                        mView.callback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
