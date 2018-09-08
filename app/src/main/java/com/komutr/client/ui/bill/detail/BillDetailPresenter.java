package com.komutr.client.ui.bill.detail;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.BillDetail;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Wallet;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BillDetailPresenter extends AppBasePresenter<BillDetailView> {

    @Inject
    public BillDetailPresenter() {

    }

    @Override
    public void onAttached() {
    }

    /**
     * 请求用户钱包数据
     */
    public void requestBillDetail(String billId) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "sales.billInfo");
        query.put("auth_key", authKey);
        query.put("id", billId);
        Disposable disposable = requestStore.get().commonRequest(query).doOnSuccess(new Consumer<RespondDO>() {
            @Override
            public void accept(RespondDO respondDO) {
                //{"amount":"40.00","bill_id":3,"chauffeur":{"name":"asdsasda","phone":"21111111"},"create_at":"2018-08-31 05:39:37","item":{"id":2,"name":"Scan QR code to buy tickets "},"order_number":"B16T1535708377R7","status":1,"type":"less","user":{"name":"dsfsdafsdfsad","phone":"211111101"}}
                if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                    BillDetail billDetail = JSON.parseObject(respondDO.getData(), BillDetail.class);
                    respondDO.setObject(billDetail);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("registeredOrLogin", respondDO.toString());
                        mView.billDetailCallBack(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        mView.billDetailCallBack(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void deleteBill(String billId) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "sales.deleteBill");
        query.put("auth_key", authKey);
        query.put("id", billId);
        Disposable disposable = requestStore.get().commonRequest(query).doOnSuccess(new Consumer<RespondDO>() {
            @Override
            public void accept(RespondDO respondDO) {
                if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                    JSONObject result = JSON.parseObject(respondDO.getData());
                    if (result != null) {
                        String deleteId = result.getString("id");
                        respondDO.setObject(deleteId);
                    }
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("registeredOrLogin", respondDO.toString());
                        mView.deleteBillCallBack(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        mView.deleteBillCallBack(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
