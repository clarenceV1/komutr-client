package com.komutr.client.ui.main;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.cai.framework.permission.RxPermissions;
import com.cai.framework.widget.dialog.GodDialog;
import com.komutr.client.R;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Service;
import com.komutr.client.been.User;
import com.komutr.client.common.Constant;
import com.komutr.client.event.EventPostInfo;
import com.komutr.client.event.PermissionEvent;
import com.komutr.client.ui.qrcode.activity.MipcaActivityCapture;

import org.greenrobot.eventbus.EventBus;

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


    /**
     * 退出登录
     */
    public void logout() {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "customer.logout");
        query.put("auth_key", authKey);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if (respondDO.isStatus()) {
                            userInfoDao.get().logout();
                            messageDao.get().deleteAll();
                            EventBus.getDefault().post(new EventPostInfo(EventPostInfo.REFRESH_MY_TRIPS));
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


    public void callPhone(final FragmentActivity activity, final String phoneNum) {
        RxPermissions permissions = new RxPermissions(activity);
        Disposable disposable = permissions.request(Manifest.permission.CALL_PHONE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) throws Exception {
                showCallPhoneDialog(activity,granted,phoneNum);
            }
        });
        mCompositeSubscription.add(disposable);
    }




    /**
     * 显示退款、删除订单对话框
     */
    private void showCallPhoneDialog(final Activity activity,final boolean granted, final String phone) {
        new GodDialog.Builder(activity)
                .setTitle(activity.getString(granted ? R.string.phone_call : R.string.permissions_des))
                .setMessage(granted ? activity.getString(R.string.make_call,phone) : activity.getString(R.string.authorize_permissions_prompt))
                .setNegativeButton(activity.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                      ToastUtils.showShort("点击取消了");
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent;
                        if (!granted){
                            intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            intent.setData(Uri.parse("package:" + activity.getPackageName()));
                        }else {
                            intent = new Intent(Intent.ACTION_CALL);// 创建一个意图,指定其动作为拨打电话
                            intent.setData(Uri.parse("tel:" + phone));// 指定将要拨出的号码
                        }
                        activity.startActivity(intent);
                    }
                }).build().show();
    }

    public void requestPhoneAndAppVersion() {
        Map<String, Object> query = new HashMap<>();
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
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void startGoScanPayPage(final FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        Disposable disposable = permissions.request(Manifest.permission.CAMERA).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) {
                if (granted) {
                    activity.startActivity(new Intent(activity, MipcaActivityCapture.class));
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 请求权限
     *
     * @param activity
     */
    public void requestPermissions(FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        Disposable disposable = permissions.request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.ACCESS_WIFI_STATE
        ).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) {
                EventBus.getDefault().post(new PermissionEvent());
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
