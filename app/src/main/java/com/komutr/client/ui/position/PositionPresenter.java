package com.komutr.client.ui.position;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.Position;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.Constant;
import com.komutr.client.ui.wallet.WalletView;

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

    @Inject
    public PositionPresenter() {

    }

    @Override
    public void onAttached() {
    }

    public void initData(boolean isStartPosition, int bigArea, int province) {
        if(isStartPosition){
            type = "beg";
        }else{
            type = "end";
        }
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
        query.put("auth_key", Constant.AUTH_KEY);
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
                        if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                            PhoneCode phoneCode = JSON.parseObject(respondDO.getData(), PhoneCode.class);
                            respondDO.setObject(phoneCode);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.requestListCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.requestListCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
