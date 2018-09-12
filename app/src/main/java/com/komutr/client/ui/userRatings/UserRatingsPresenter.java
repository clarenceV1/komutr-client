package com.komutr.client.ui.userRatings;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.logger.Logger;
import com.komutr.client.R;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.OrderDetail;
import com.komutr.client.been.RespondDO;
import com.komutr.client.ui.wallet.WalletView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class UserRatingsPresenter extends AppBasePresenter<UserRatingsView> {

    @Inject
    public UserRatingsPresenter() {

    }

    @Override
    public void onAttached() {


    }


    public void userRatings(String orderId, String shiftId,String chauffeurId,String content,Float score) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "customer.reviewChauffeur");
        query.put("auth_key", authKey);
        query.put("order_id", orderId);
        query.put("shift_id", shiftId);
        query.put("chauffeur_id", chauffeurId);
        query.put("content", content);
        query.put("review_score", score);
        Disposable disposable = requestStore.get().commonRequest(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.userRatingsCallBack(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        respondDO.setMsg(GodBaseApplication.getAppContext().getString(R.string.rating_fail));
                        mView.userRatingsCallBack(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }

}
