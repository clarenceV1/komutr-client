package com.komutr.client.ui.Purchase;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.BuySellTicketComment;
import com.komutr.client.been.BuyTicket;
import com.komutr.client.been.RespondDO;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ReviewPurchasePresenter extends AppBasePresenter<ReviewPurchaseView> {

    @Inject
    public ReviewPurchasePresenter() {

    }

    @Override
    public void onAttached() {
    }

    /**
     * 购票
     *
     * @param routeId    路线id
     * @param shiftId    班次id
     * @param begStation 开始站
     * @param endStation 结束站
     */
    public void purchaseTicket(String routeId, String shiftId, String begStation, String endStation) {
        String authkey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "sales.buyTicket");
        query.put("auth_key", authkey);
        query.put("route_id", routeId);
        query.put("shift_id", shiftId);
        query.put("beg_station", begStation);
        query.put("end_station", endStation);
        query.put("qty", 1);
        Disposable disposable = requestStore.get().commonRequest(query).doOnSuccess(new Consumer<RespondDO>() {
            @Override
            public void accept(RespondDO respondDO) {
                //{"amount":40,"order_id":"65","qty":"1","ticket_record":29}
                if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                    BuyTicket buyTicket = JSON.parseObject(respondDO.getData(), BuyTicket.class);
                    respondDO.setObject(buyTicket);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("registeredOrLogin", respondDO.toString());
                        mView.purchaseTicketCallBack(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.purchaseTicketCallBack(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 获取买票下的备注信息
     */
    public void requestComment() {
        Map<String, Object> query = new HashMap<>();
        query.put("m", "system.ticketNote");
        query.put("auth_key", Constant.AUTH_KEY);
        Disposable disposable = requestStore.get().commonRequest(query).doOnSuccess(new Consumer<RespondDO>() {
            @Override
            public void accept(RespondDO respondDO) {
                //{"buy":{"content":"dsfdsfsf","created_at":"0000-00-00 00:00:00","id":1,"title":"dcvdsfsf"},"refund":{"content":"dsfdsfsf","created_at":"0000-00-00 00:00:00","id":1,"title":"dcvdsfsf"}}
                if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                    JSONObject jsonObject = JSON.parseObject(respondDO.getData());
                    String buy = jsonObject.getString("buy");
                    if (!TextUtils.isEmpty(buy)) {
                        BuySellTicketComment comment = JSON.parseObject(buy, BuySellTicketComment.class);
                        respondDO.setObject(comment);
                    }
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("registeredOrLogin", respondDO.toString());
                        mView.commentCallBack(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.commentCallBack(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);

    }
}
