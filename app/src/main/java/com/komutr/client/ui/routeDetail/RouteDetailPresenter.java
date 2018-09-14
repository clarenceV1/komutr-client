package com.komutr.client.ui.routeDetail;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.RoutesInfo;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.common.Constant;
import com.komutr.client.ui.routes.SearchRoutesView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class RouteDetailPresenter extends AppBasePresenter<RouteDetailView> {

    @Inject
    public RouteDetailPresenter() {

    }

    @Override
    public void onAttached() {
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
                        Log.d("routesInfo", respondDO.toString());
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
