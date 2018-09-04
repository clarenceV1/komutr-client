package com.komutr.client.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.cai.framework.logger.Logger;
import com.cai.framework.permission.RxPermissions;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.User;
import com.komutr.client.common.Constant;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MainPresenter extends AppBasePresenter<MainView> {

    @Inject
    public MainPresenter() {

    }

    @Override
    public void onAttached() {

    }

    public User switcher() {
        userInfoDao.get().switcher();
        return userInfoDao.get().getUser();
    }

    public void logout() {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, String> query = new HashMap<>();
        query.put("m", "customer.logout");
        query.put("auth_key", authKey);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if (respondDO.isStatus()) {
                            userInfoDao.get().logout();
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.logout(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        mView.logout(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
    public boolean isLogin(){
        return userInfoDao.get().isLogin();
    }


    public void callPhone(FragmentActivity activity, final String phoneNum) {
        RxPermissions permissions = new RxPermissions(activity);
        Disposable disposable = permissions.request(Manifest.permission.CALL_PHONE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    callPhone(phoneNum);
                } else {
                    ToastUtils.showShort("one");
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }

    @SuppressLint("MissingPermission")
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(data);
        context.startActivity(intent);
    }
    public void requestServicePhone(){
        Map<String, String> query = new HashMap<>();
        query.put("m", "system.serviceInfo");
        query.put("auth_key", Constant.AUTH_KEY);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if (respondDO.isStatus()) {

                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.servicePhoneCallBack(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO=new RespondDO();
                        mView.servicePhoneCallBack(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
