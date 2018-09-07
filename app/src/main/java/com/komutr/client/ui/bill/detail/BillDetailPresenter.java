package com.komutr.client.ui.bill.detail;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
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
    public void requestBillDetail() {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, String> query = new HashMap<>();
        query.put("m", "payment.customerAmount");
        query.put("auth_key", authKey);
        Disposable disposable = requestStore.get().commonRequest(query).doOnSuccess(new Consumer<RespondDO>() {
            @Override
            public void accept(RespondDO respondDO) {
                //{"balance":"0.00","is_active":1,"outlay_amount":"0.00","recharge_total":"0.00"}
                if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                    Wallet wallet = JSON.parseObject(respondDO.getData(), Wallet.class);
                    respondDO.setObject(wallet);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("registeredOrLogin", respondDO.toString());
//                        mView.walletCallBack(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
//                        mView.walletCallBack(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
