package com.komutr.client.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.cai.framework.permission.RxPermissions;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.BuildConfig;
import com.komutr.client.R;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.AppVersion;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Service;
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

    public boolean isLogin() {
        return userInfoDao.get().isLogin();
    }


    public void callPhone(FragmentActivity activity, final String phoneNum) {
        RxPermissions permissions = new RxPermissions(activity);
        Disposable disposable = permissions.request(Manifest.permission.CALL_PHONE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                if (granted) {
                    callPhone(phoneNum);
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

    public void requestPhoneAndAppVersion() {
        Map<String, String> query = new HashMap<>();
        query.put("m", "system.serviceInfo");
        query.put("auth_key", Constant.AUTH_KEY);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        //{"app":{"update_url":"http://baidu.com","version":"1.00"},"telphone":"1111111"}
                        if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                            Service service = JSON.parseObject(respondDO.getData(), Service.class);
                            respondDO.setObject(service);
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.serviceCallBack(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        mView.serviceCallBack(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    public  void openBrowser(String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
