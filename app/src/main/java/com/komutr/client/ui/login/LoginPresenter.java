package com.komutr.client.ui.login;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.logger.Logger;
import com.example.clarence.utillibrary.UniqueIdUtils;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.User;
import com.komutr.client.common.Constant;
import com.komutr.client.dao.UserInfoDao;
import com.komutr.client.event.EventPostInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class LoginPresenter extends AppBasePresenter<LoginView> {

    @Inject
    Lazy<UserInfoDao> userInfoDao;

    @Inject
    public LoginPresenter() {

    }

    @Override
    public void onAttached() {
    }


    public void registeredOrLogin(String code, String phone, String verTokenKey) {
        Map<String, Object> query = new HashMap<>();
        query.put("m", "customer.registeredOrLogin");
        query.put("auth_key", Constant.AUTH_KEY);
        query.put("ver_token_key", verTokenKey);
        query.put("app_key", UniqueIdUtils.getDeviceInfo(GodBaseApplication.getAppContext(), UniqueIdUtils.DEVICES_INFO.IMEI));
        query.put("code", code);
        query.put("phone", phone);
        String pushClientId = dataStore.get().getGeTuiPushClientId();
        query.put("device_code", pushClientId);
        Disposable disposable = requestStore.get().commonRequest(query).doOnSuccess(new Consumer<RespondDO>() {
            @Override
            public void accept(RespondDO respondDO) {
                if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                    User userInfo = JSON.parseObject(respondDO.getData(), User.class);
                    respondDO.setObject(userInfo);
                    if (userInfoDao != null) {
                        userInfoDao.get().saveAndDelete(userInfo);
                    }
                    EventBus.getDefault().post(new EventPostInfo(EventPostInfo.UPDATE_PERSON_INFO_SUCCESS));
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("registeredOrLogin", respondDO.toString());
                        mView.registeredOrLoginCallBack(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(-1);
                        mView.registeredOrLoginCallBack(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 获取验证码
     *
     * @param phone
     */
    public void verificationCode(final String phone) {
        Map<String, Object> query = new HashMap<>();
        query.put("m", "customer.verification");
        query.put("auth_key", Constant.AUTH_KEY);
        query.put("phone", phone);
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
