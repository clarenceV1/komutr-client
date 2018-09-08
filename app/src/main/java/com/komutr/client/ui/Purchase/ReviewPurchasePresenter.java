package com.komutr.client.ui.Purchase;

import android.text.TextUtils;
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

public class ReviewPurchasePresenter extends AppBasePresenter<ReviewPurchaseView> {

    @Inject
    public ReviewPurchasePresenter() {

    }

    @Override
    public void onAttached() {
    }

    /**
     * 购票
     * @param routeId 路线id
     * @param shiftId 班次id
     * @param begStation 开始站
     * @param endStation 结束站
     */
    public void purchaseTicket(int routeId,int shiftId,int begStation,int endStation) {
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
                if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {

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
}
