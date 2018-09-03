package com.komutr.client.ui.personInfo;

import android.text.TextUtils;

import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.User;
import com.komutr.client.ui.wallet.WalletView;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PersonInfoPresenter extends AppBasePresenter<PersonInfoView> {

    @Inject
    public PersonInfoPresenter() {

    }

    @Override
    public void onAttached() {
    }

    public User getUserInfo(){
        return userInfoDao.get().getUser();
    }

    public void requestUserInfo() {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, String> query = new HashMap<>();
        query.put("m", "customer.myData");
        query.put("auth_key", authKey);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        //{"app_auth_key":"JCNnKH7Y6HCFFuKK","avatar":"","avatar_thum":"","id":19,"phone":"13779926287","username":"vcf"}
                        if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {

                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
//                        mView.callb(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
//                        mView.nextAreaCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
