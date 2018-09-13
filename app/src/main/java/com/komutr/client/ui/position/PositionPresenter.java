package com.komutr.client.ui.position;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cai.framework.logger.Logger;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.Position;
import com.komutr.client.been.RespondDO;
import com.komutr.client.ui.FrequentlyStation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PositionPresenter extends AppBasePresenter<PositionView> {

    String type;//beg 始发站 end 结束站
    int bigArea;//大区 ID
    int province;//大区下面的行政单位 ID

    String authKey;

    @Inject
    public PositionPresenter() {

    }

    @Override
    public void onAttached() {
    }

    public void initData(boolean isStartPosition, int bigArea, int province) {
        this.authKey = userInfoDao.get().getAppAuth();
        this.type = isStartPosition ? "beg" : "end";
        this.bigArea = bigArea;
        this.province = province;
    }

    public List<Position> getTestList() {

        List<Position> list = new ArrayList<>();
        list.add(new Position());
        list.add(new Position());
        list.add(new Position());
        list.add(new Position());
        list.add(new Position());
        list.add(new Position());
        return list;
    }

    public void requestMore() {

    }

    public void requestList(String value, int offset, int limit) {
        Map<String, Object> query = new HashMap<>();
        query.put("m", "station.searchEndAndBegStation");
        query.put("auth_key", authKey);
        query.put("value", value);
        query.put("offset", offset);
        query.put("limit", limit);
        query.put("type", type);
        query.put("big_area", bigArea);
        query.put("province", province);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if (respondDO.isStatus() && !StringUtils.isEmpty(respondDO.getData())) {
                            List<Position> positionList = JSON.parseArray(respondDO.getData(), Position.class);
                            respondDO.setObject(positionList);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.frequentlyStationCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.frequentlyStationCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }


    public void requestFrequentlyStation() {
        Map<String, Object> query = new HashMap<>();
        query.put("m", "station.customerFrequentlyUseStation");
        query.put("auth_key", authKey);
        query.put("limit", "0,10");
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if (respondDO.isStatus() && !StringUtils.isEmpty(respondDO.getData())) {
                            List<FrequentlyStation> frequentlyStationList = frequentlyStationDao.get().updateAll(JSON.parseArray(respondDO.getData(),FrequentlyStation.class),0,10);
                            respondDO.setObject(frequentlyStationList);
                        }else {
                            List<FrequentlyStation> frequentlyStationList = getFrequentlyStationList();
                            if(frequentlyStationList != null && !frequentlyStationList.isEmpty()){
                                respondDO.setObject(frequentlyStationList);
                                respondDO.setStatus(true);
                            }
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.frequentlyStationCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        List<FrequentlyStation> frequentlyStationList = getFrequentlyStationList();
                        if(frequentlyStationList != null && !frequentlyStationList.isEmpty()){
                            respondDO.setObject(frequentlyStationList);
                            respondDO.setStatus(true);
                        }else{
                            respondDO.setFromCallBack(-1);
                        }
                        mView.frequentlyStationCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }


    /**
     * 删除常用站点
     * @param id
     */
    public void deleteFrequentlyStation(final int id) {
        Map<String, Object> query = new HashMap<>();
        query.put("m", "station.delCustomerFrequentlyUseStation");
        query.put("auth_key", authKey);
        query.put("id", id);
        Disposable disposable = requestStore.get().commonRequest(query).doOnSuccess(new Consumer<RespondDO>() {
            @Override
            public void accept(RespondDO respondDO) {
                if (respondDO.isStatus()) {
                    frequentlyStationDao.get().delete(id);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("deleteFrequentlyStation", respondDO.toString());

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
//                      RespondDO respondDO = new RespondDO();

                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public List<FrequentlyStation> getFrequentlyStationList(){

        return frequentlyStationDao.get().getFrequentlyStationList(0,10);
    }




}
