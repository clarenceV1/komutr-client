package com.komutr.client.ui.personInfo;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.logger.Logger;
import com.cai.framework.permission.RxPermissions;
import com.example.clarence.utillibrary.FileUtils;
import com.example.clarence.utillibrary.PhotoUtils;
import com.example.clarence.utillibrary.PrivateConstant;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.R;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.User;
import com.komutr.client.common.Constant;
import com.komutr.client.event.EventPostInfo;
import com.komutr.client.ui.qrcode.activity.MipcaActivityCapture;
import com.komutr.client.ui.wallet.WalletView;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
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

    public User getUserInfo() {
        return userInfoDao.get().getUser();
    }




    public void requestUserInfo() {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "customer.myData");
        query.put("auth_key", authKey);
        Disposable disposable = requestStore.get().commonRequest(query)
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        //{"app_auth_key":"JCNnKH7Y6HCFFuKK","avatar":"","avatar_thum":"","email":"763287516@qq.com","id":19,"phone":"13779926287","username":"vcf"}
                        if (respondDO.isStatus() && !StringUtils.isEmpty(respondDO.getData())) {
                            User user = JSON.parseObject(respondDO.getData(), User.class);
                            if (user != null) {
                                userInfoDao.get().updateUser(user);
                                respondDO.setObject(user);
                                EventBus.getDefault().post(new EventPostInfo(EventPostInfo.REFRESH_PERSON_INFO_SUCCESS));
                            }

                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.callbackUserInfo(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        mView.callbackUserInfo(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 上传头像
     *
     * @param imageFile
     */
    public void uploadImage(File imageFile) {
        String authKey = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "customer.uploadImage");
        query.put("auth_key", authKey);
        query.put("image", imageFile);
        Disposable disposable = requestStore.get().commonRequest(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Logger.d(respondDO.toString());
                        mView.callbackAvatar(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setMsg(GodBaseApplication.getAppContext().getString(R.string.upload_failed));
                        mView.callbackAvatar(respondDO);
                    }
                });
        mCompositeSubscription.add(disposable);
    }


    /**
     * 打开相机
     *
     * @param activity
     */
    public void startCamera(final FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        Disposable disposable = permissions.request(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) {
                if (granted) {
                    final String photoPath = FileUtils.getSaveFilePath(PrivateConstant.FileInfo.TYPE_PHOTO, activity) + ".takePic.jpg";
                    PhotoUtils.takePhoto(activity, photoPath, Constant.ActivityReqAndRes.CHOOSE_OPEN_CAMERA);
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }


    /**
     * 选择相册
     * @param activity
     */
    public void selectAlbum(final FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        Disposable disposable = permissions.request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) {
                if (granted) {
                    PhotoUtils.choosePhoto(activity, Constant.ActivityReqAndRes.CHOOSE_SELECT_ALBUM);
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }



    /**
     * 裁剪头像
     * @param activity
     */
    public void cropAvatar(final String photoPath,final FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        Disposable disposable = permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) {
                if (granted) {
                    PhotoUtils.startCorpImage(activity, photoPath,Constant.ActivityReqAndRes.START_CUT_AVATAR);
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
