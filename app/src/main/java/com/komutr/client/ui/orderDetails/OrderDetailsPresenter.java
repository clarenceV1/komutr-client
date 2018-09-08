package com.komutr.client.ui.orderDetails;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.OrderDetail;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.Constant;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class OrderDetailsPresenter extends AppBasePresenter<OrderDetailsView> {

    @Inject
    public OrderDetailsPresenter() {

    }

    @Override
    public void onAttached() {
    }

    public void getOrderDetail(String orderId, boolean hasComment) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "sales.getOrderData");
        query.put("auth_key", authKey);
        query.put("id", orderId);
        if (hasComment) {
            query.put("status", true);
        }
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                            OrderDetail orderDetail = JSON.parseObject(respondDO.getData(), OrderDetail.class);
                            respondDO.setObject(orderDetail);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.orderDetailCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.orderDetailCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 退票
     *
     * @param orderId
     * @param type    1 退款 退票 2退票
     */
    public void refundTicket(String orderId, int type) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "sales.getOrderData");
        query.put("auth_key", authKey);
        query.put("id", orderId);
        query.put("type", type);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                            OrderDetail orderDetail = JSON.parseObject(respondDO.getData(), OrderDetail.class);
                            respondDO.setObject(orderDetail);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.orderDetailCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.orderDetailCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
