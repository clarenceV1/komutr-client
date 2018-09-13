package com.komutr.client.ui.main.fragment.book;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.google.android.gms.maps.model.LatLng;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Station;
import com.komutr.client.common.Constant;
import com.komutr.client.ui.map.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

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
    public void requestNearby(double latitude, double longitude, int offset, int limit) {
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
                            respondDO.setObject(stationList);
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

    public void getNearestDistance(final LatLng location, final List<Station> nearbys) {
        if (location == null || nearbys == null || nearbys.size() == 0) {
            return;
        }
        Disposable disposable = Single.create(new SingleOnSubscribe<Station>() {
            @Override
            public void subscribe(SingleEmitter<Station> e) {
                double distance = 0;
                Station nearest = null;
                for (Station nearby : nearbys) {
                    double between = MapUtils.getDistance(location.longitude, location.latitude, nearby.getLongitude(), nearby.getLatitude());
                    if (distance == 0 || between < distance) {
                        distance = between;
                        nearest = nearby;
                    }
                }
                e.onSuccess(nearest);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Station>() {
                    @Override
                    public void accept(Station stations){
                        mView.nearestDistanceCallback(stations);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
