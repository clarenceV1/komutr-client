package com.komutr.client.ui.phoneNumber;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.User;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class PhoneNumberPresenter extends AppBasePresenter<PhoneNumberView> {

    @Inject
    public PhoneNumberPresenter() {

    }

    @Override
    public void onAttached() {
    }
    public User getInfo(){
        return userInfoDao.get().getUser();
    }

    public void changePhoneNumber(String phone, PhoneCode phoneCode) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, String> query = new HashMap<>();
        query.put("m", "customer.changeMyPhoneNumber");
        query.put("auth_key", authKey);
        query.put("phone", phone);
        query.put("ver_token_key",phoneCode.getVer_token_key());
        query.put("code", phoneCode.getCode());
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        //[{"big_area_code":"100","code":"100","id":1,"is_active":1,"name":"lkjlk"}]
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
    /**
     * 获取验证码
     *
     * @param phone
     * @param type  1 注册 2 找回密码 3 重置密码
     */
    public void verificationCode(final String phone, int type) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, String> query = new HashMap<>();
        query.put("m", "customer.verification");
        query.put("auth_key", authKey);
        query.put("phone", phone);
        query.put("type", type + "");
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if(respondDO.isStatus()&&!TextUtils.isEmpty(respondDO.getData())){
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
                        mView.verificationCodeCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.verificationCodeCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
