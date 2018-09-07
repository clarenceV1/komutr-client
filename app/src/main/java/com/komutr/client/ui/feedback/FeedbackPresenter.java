package com.komutr.client.ui.feedback;

import android.util.Log;

import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.RespondDO;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class FeedbackPresenter extends AppBasePresenter<FeedbackView> {

    @Inject
    public FeedbackPresenter() {

    }

    @Override
    public void onAttached() {
    }

    /**
     * 意见反馈
     */
    public void requestFeedBack(String content) {
        String auth_key = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "system.feedback");
        query.put("auth_key", auth_key);
        query.put("content", content);
        Disposable disposable = requestStore.get().commonRequest(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("requestFeedBack",respondDO.toString());
                        mView.callback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();//status= false就是异常
                        mView.callback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
