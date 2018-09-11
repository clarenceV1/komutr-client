package com.komutr.client.ui.bill;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.Bill;
import com.komutr.client.been.RespondDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BillPresenter extends AppBasePresenter<BillView> {

    @Inject
    public BillPresenter() {

    }

    @Override
    public void onAttached() {
    }

    public void requestMore() {

    }

    public void requestList() {
        String authKey= userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "sales.getHistory");
        query.put("auth_key", authKey);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        //[{"created":"2018-08-31 05:39:37","id":3,"item":{"amount":"40.00","item_name":"Scan QR code to buy tickets ","type":"less"}},{"created":"2018-08-31 05:39:47","id":4,"item":{"amount":"40.00","item_name":"Scan QR code to buy tickets ","type":"less"}},{"created":"2018-08-31 05:39:54","id":5,"item":{"amount":"40.00","item_name":"Scan QR code to buy tickets ","type":"less"}},{"created":"2018-08-31 05:39:59","id":6,"item":{"amount":"40.00","item_name":"Scan QR code to buy tickets ","type":"less"}},{"created":"2018-08-31 05:40:05","id":7,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-08-31 05:54:08","id":8,"item":{"amount":"0.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-03 03:17:11","id":9,"item":{"amount":"100.00","item_name":"KOMUTRPAY top-up","type":"add"}},{"created":"2018-09-04 09:55:14","id":10,"item":{"amount":"10.00","item_name":"Refund","type":"add"}},{"created":"2018-09-04 05:20:53","id":11,"item":{"amount":"","item_name":"","type":""}},{"created":"2018-09-04 05:25:02","id":12,"item":{"amount":"","item_name":"","type":""}},{"created":"2018-09-04 05:25:05","id":13,"item":{"amount":"","item_name":"","type":""}},{"created":"2018-09-04 05:25:07","id":14,"item":{"amount":"","item_name":"","type":""}},{"created":"2018-09-06 03:27:15","id":17,"item":{"amount":"","item_name":"","type":""}},{"created":"2018-09-06 03:27:59","id":18,"item":{"amount":"","item_name":"","type":""}},{"created":"2018-09-06 03:28:34","id":19,"item":{"amount":"","item_name":"","type":""}},{"created":"2018-09-06 03:30:31","id":20,"item":{"amount":"40.00","item_name":"Scan QR code to buy tickets ","type":"less"}},{"created":"2018-09-06 04:06:49","id":21,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-06 04:06:49","id":22,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-06 04:06:58","id":23,"item":{"amount":"40.00","item_name":"Scan QR code to buy tickets ","type":"less"}},{"created":"2018-09-07 10:43:49","id":24,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 10:43:55","id":25,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 10:44:19","id":26,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 10:48:40","id":27,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 10:48:44","id":28,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 10:48:48","id":29,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 10:49:18","id":30,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 10:49:24","id":31,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 10:49:34","id":32,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 10:56:12","id":33,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 10:56:43","id":34,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 10:57:06","id":35,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 11:00:46","id":36,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 11:04:33","id":37,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-07 11:04:53","id":38,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-08 03:51:11","id":39,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-08 03:52:01","id":40,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}},{"created":"2018-09-08 04:09:27","id":41,"item":{"amount":"40.00","item_name":"在线购票","type":"less"}}]
                        if(respondDO.isStatus()&&!StringUtils.isEmpty(respondDO.getData())){
                            List<Bill> billList = JSON.parseArray(respondDO.getData(), Bill.class);
                            respondDO.setObject(billList);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.billListCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.billListCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
