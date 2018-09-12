package com.komutr.client.ui.orderDetails;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.logger.Logger;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.R;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.BuySellTicketDetails;
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
                        respondDO.setMsg(GodBaseApplication.getAppContext().getString(R.string.request_failed));
                        mView.orderDetailCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }


    /**
     * 删除订单
     *
     * @param orderId
     */
    public void deleteOrder(String orderId) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "sales.delOrder");
        query.put("auth_key", authKey);
        query.put("id", orderId);
        Disposable disposable = requestStore.get().commonRequest(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.operateCallBack(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        respondDO.setMsg(GodBaseApplication.getAppContext().getString(R.string.order_fail));
                        mView.operateCallBack(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 退票
     *
     * @param orderId
     * @param type    离发车时间 1 大于30分钟 2 是小于30 分钟
     */
    public void refundTicket(String orderId, int type) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "sales.refundTicket");
        query.put("auth_key", authKey);
        query.put("order_id", orderId);
        query.put("type", type);
        Disposable disposable = requestStore.get().commonRequest(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.operateCallBack(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        respondDO.setMsg(GodBaseApplication.getAppContext().getString(R.string.refund_fail_));
                        mView.operateCallBack(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }


    /**
     * 获取买票下的备注信息
     */
    public void requestImportant() {
        Map<String, Object> query = new HashMap<>();
        query.put("m", "system.ticketNote");
        query.put("auth_key", Constant.AUTH_KEY);
        Disposable disposable = requestStore.get().commonRequest(query).doOnSuccess(new Consumer<RespondDO>() {
            @Override
            public void accept(RespondDO respondDO) {
                if (respondDO.isStatus() && !StringUtils.isEmpty(respondDO.getData())) {
                    respondDO.setObject(JSON.parseObject(respondDO.getData(), BuySellTicketDetails.class));
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("requestBuy", respondDO.toString());
                        mView.importantCallBack(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();

                        respondDO.setFromCallBack(-1);
                        mView.importantCallBack(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
