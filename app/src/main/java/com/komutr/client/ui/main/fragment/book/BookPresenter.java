package com.komutr.client.ui.main.fragment.book;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.RoutesInfo;
import com.komutr.client.been.Station;
import com.komutr.client.common.Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BookPresenter extends AppBasePresenter<BookView> {

    @Inject
    public BookPresenter() {

    }

    @Override
    public void onAttached() {

    }

    /**
     * 查询最近的站点
     *
     * @param longitude
     * @param latitude
     * @param offset
     * @param limit
     */
    public void requestNearby(double longitude, double latitude, int offset, int limit) {
        Map<String, Object> query = new HashMap<>();
        query.put("m", "station.nearby");
        query.put("auth_key", Constant.AUTH_KEY);
        query.put("longitude", longitude);
        query.put("latitude", latitude);
        query.put("offset", offset);
        query.put("limit", limit);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        //[{"id":1,"latitude":"26.11395","longitude":"118.07404","name":"厦门站"}]
                        if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                            List<Station> stationList = JSON.parseArray(respondDO.getData(), Station.class);
                            if (stationList != null && stationList.size() > 0) { //附近站点应该是一个才对
                                respondDO.setObject(stationList.get(0));
                            }
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.requestNearbyCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        mView.requestNearbyCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public void routesInfo(final String routeId, String shiftId) {
        Map<String, Object> query = new HashMap<>();
        query.put("m", "station.viewRoute");
        query.put("auth_key", Constant.AUTH_KEY);
        query.put("route_id", routeId);
        query.put("shift_id", shiftId);
        Disposable disposable = requestStore.get().commonRequest(query).doOnSuccess(new Consumer<RespondDO>() {
            @Override
            public void accept(RespondDO respondDO) {
                if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                    RoutesInfo routesInfo = JSON.parseObject(respondDO.getData(),RoutesInfo.class);
                    respondDO.setObject(routesInfo);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("registeredOrLogin", respondDO.toString());
                        mView.routesInfoCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                      mView.routesInfoCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
