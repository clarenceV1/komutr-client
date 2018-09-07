package com.komutr.client.ui.email;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.Constant;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class BindEmailPresenter extends AppBasePresenter<BindEmailView> {

    @Inject
    public BindEmailPresenter() {

    }

    @Override
    public void onAttached() {
    }

    public void bindEmail(String email, String code, PhoneCode phoneCode) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "customer.changeEmail");
        query.put("auth_key", authKey);
        query.put("code", code);
        query.put("email", email);
        query.put("ver_token_key", phoneCode.getVer_token_key());
        Disposable disposable = requestStore.get().commonRequest(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.bindEmailCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.bindEmailCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);

    }

    /**
     * 检查邮箱是否有效
     *
     * @param email
     */
    public void checkEmail(String email) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "customer.verificationEmail");
        query.put("auth_key", authKey);
        query.put("email", email);
        query.put("type", "1");// 1 注册 2 找回密码 3 重置密码 4.重新绑定
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
                        mView.checkEmail(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.checkEmail(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);

    }
}
