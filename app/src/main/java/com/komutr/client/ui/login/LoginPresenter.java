package com.komutr.client.ui.login;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.User;
import com.komutr.client.dao.UserInfoDao;
import com.komutr.client.event.LoginEvent;

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


    /**
     * 请求登录注册
     * @param requestFlag
     */
    public void registeredOrLogin(int requestFlag) {
        mView.onBegin();
        Disposable disposable = requestStore.get().commonRequest(mView.getParams(requestFlag)).doOnSuccess(new Consumer<RespondDO>() {
            @Override
            public void accept(RespondDO respondDO) {
                if (respondDO.isStatus()&& !TextUtils.isEmpty(respondDO.getData())) {
                    User userInfo = JSON.parseObject(respondDO.getData(), User.class);
                    respondDO.setObject(userInfo);
                    if (userInfoDao != null) {
                        userInfoDao.get().saveAndDelete(userInfo);
                    }
                    EventBus.getDefault().post(new LoginEvent(1, userInfo));
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.onFinish();
                        if (respondDO.isStatus()) { //成功
                            mView.registeredOrLoginCallBack(respondDO);
                        } else {//失败
                            mView.showToastMsg(respondDO.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        mView.onFinish();
                        mView.onError(throwable.getMessage());
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 获取验证码
     *
     * @param requestFlag
     *
     */
    public void verificationCode(int requestFlag) {
        mView.onBegin();

        Disposable disposable = requestStore.get().commonRequest(mView.getParams(requestFlag))
                .observeOn(AndroidSchedulers.mainThread()).doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if(respondDO.isStatus()&&!TextUtils.isEmpty(respondDO.getData())){
                            PhoneCode phoneCode = JSON.parseObject(respondDO.getData(), PhoneCode.class);
                            respondDO.setObject(phoneCode);
                        }
                    }
                })
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        mView.onFinish();
                        Logger.d(respondDO.toString());
                        if (respondDO.isStatus()) { //成功
                            mView.verificationCodeCallback(respondDO);
                        } else {//失败
                            mView.showToastMsg(respondDO.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        mView.onFinish();
                        mView.onError(throwable.getMessage());
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
