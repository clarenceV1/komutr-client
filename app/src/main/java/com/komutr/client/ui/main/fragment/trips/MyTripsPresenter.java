package com.komutr.client.ui.main.fragment.trips;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.OrderDetail;
import com.komutr.client.been.RespondDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MyTripsPresenter extends AppBasePresenter<MyTripsView> {


    @Inject
    public MyTripsPresenter() {

    }

    @Override
    public void onAttached() {

    }


    public void requestMore() {

    }

    /**
     * 请求订单列表
     *
     * @param start 请求第几条开始
     * @param size  请求数量
     */
    public void requestList(int start, int size) {
        String authkey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "sales.orderList");
        query.put("auth_key", authkey);
        query.put("limit", start + "," + size);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                            List<OrderDetail> phoneCode = JSON.parseArray(respondDO.getData(), OrderDetail.class);
                            respondDO.setObject(phoneCode);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.orderListCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.orderListCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
