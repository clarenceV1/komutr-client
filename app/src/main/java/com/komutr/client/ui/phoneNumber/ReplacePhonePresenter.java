package com.komutr.client.ui.phoneNumber;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.User;
import com.komutr.client.event.EventPostInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class ReplacePhonePresenter extends AppBasePresenter<ReplacePhoneView> {

    @Inject
    public ReplacePhonePresenter() {

    }

    @Override
    public void onAttached() {
    }
    public User getInfo(){
        return userInfoDao.get().getUser();
    }

    public void changePhoneNumber(String phone,String code, PhoneCode phoneCode) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "customer.changeMyPhoneNumber");
        query.put("auth_key", authKey);
        query.put("phone", phone);
        query.put("ver_token_key",phoneCode.getVer_token_key());
        query.put("code", code);
        Disposable disposable = requestStore.get().commonRequest(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.changePhoneNumberCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        mView.changePhoneNumberCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
    /**
     * 获取验证码
     *
     * @param phone
     */
    public void verificationCode(final String phone,final int fromCallBack) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "customer.verification");
        query.put("auth_key", authKey);
        query.put("phone", phone);
        query.put("type","4");// 1 注册 2 找回密码 3 重置密码 4.重新绑定
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if(respondDO.isStatus()&&!TextUtils.isEmpty(respondDO.getData())){
                            PhoneCode phoneCode = JSON.parseObject(respondDO.getData(), PhoneCode.class);
                            respondDO.setObject(phoneCode);
                            User userInfo = new User();
                            userInfo.setPhone(phone);
                            userInfoDao.get().updateUser(userInfo);
                            EventBus.getDefault().post(new EventPostInfo(EventPostInfo.UPDATE_PERSON_INFO_SUCCESS));
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        respondDO.setFromCallBack(fromCallBack);
                        mView.verificationCodeCallback(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setFromCallBack(fromCallBack);
                        mView.verificationCodeCallback(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
