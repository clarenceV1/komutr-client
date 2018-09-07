package com.komutr.client.ui.routes;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.been.User;
import com.komutr.client.common.Constant;
import com.komutr.client.event.LoginEvent;
import com.komutr.client.ui.wallet.WalletView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SearchRoutesPresenter extends AppBasePresenter<SearchRoutesView> {

    @Inject
    public SearchRoutesPresenter() {

    }

    @Override
    public void onAttached() {
    }


    public void requestMore() {

    }

    public void requestList() {
    }

    public List<SearchRoutes> getTestList() {
        List<SearchRoutes> newdatas = new ArrayList<>();
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());

        return newdatas;
    }

    public void searchRoutes(int begStationId, int endStationId, int offset, int limit) {
        Map<String, Object> query = new HashMap<>();
        query.put("m", "station.route");
        query.put("auth_key", Constant.AUTH_KEY);
        query.put("beg_station", begStationId + "");
        query.put("end_station", endStationId + "");
        query.put("offset", offset + "");
        query.put("limit", limit + "");
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
                        mView.searchRoutes(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.searchRoutes(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
