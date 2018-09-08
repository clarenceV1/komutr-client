package com.komutr.client.ui.recharge;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RechargePackage;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.Constant;
import com.komutr.client.ui.wallet.WalletView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RechargePresenter extends AppBasePresenter<RechargeView> {

    @Inject
    public RechargePresenter() {

    }

    @Override
    public void onAttached() {
    }


    public void requestRechargePackage() {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "sales.rechargePackge");
        query.put("auth_key", authKey);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        //{"packge":[{"amount":"100.00","description":"重置抢","discount":"0.00","id":1,"type":"1"},{"amount":"100.00","discount":"0.00","id":2,"type":"1"},{"amount":"100.00","discount":"0.00","id":3,"type":"1"},{"amount":"100.00","discount":"0.00","id":4,"type":"1"}],"payment":{"1":"菲律宾在线充值模式1","2":"菲律宾在线充值模式1","3":"菲律宾在线充值模式1"}}
                        if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                            RechargePackage rechargePackage = JSON.parseObject(respondDO.getData(), RechargePackage.class);
                            respondDO.setObject(rechargePackage);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.rechargePackageCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        mView.rechargePackageCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);

    }
}
