package com.komutr.client.ui.personInfo;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.logger.Logger;
import com.cai.framework.permission.RxPermissions;
import com.example.clarence.utillibrary.FileUtils;
import com.example.clarence.utillibrary.PhotoUtils;
import com.example.clarence.utillibrary.PrivateConstant;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.R;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.PersonInfo;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.UploadImage;
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
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

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


    /**
     * 更新用户信息
     *
     * @param avatar   先上传图片 在提交
     * @param birthday 生日
     * @param big_area 大区id
     * @param province 省id
     * @param sex      性别 1男 2女
     */
    public void updateMyData(String avatar, String birthday, int big_area, int province, int sex) {
        Map<String, Object> query = new HashMap<>();
        String auth_key = userInfoDao.get().getAppAuth();
        query.put("m", "customer.updateMyData");
        query.put("auth_key", auth_key);
        if (!StringUtils.isEmpty(avatar)) {
            query.put("avatar", avatar);
        }

        if (!StringUtils.isEmpty(birthday)) {
            query.put("birthday", birthday);
        }

        if (big_area != -1) {
            query.put("big_area", big_area);
        }
        if (province != -1) {
            query.put("province", province);
        }
        if (sex != -1) {
            query.put("sex", sex);
        }
        Disposable disposable = requestStore.get().commonRequest(query).doOnSuccess(new Consumer<RespondDO>() {
            @Override
            public void accept(RespondDO respondDO) {
                if (respondDO.isStatus() && !StringUtils.isEmpty(respondDO.getData())) {
                    User userInfo = JSON.parseObject(respondDO.getData(), User.class);
                    userInfoDao.get().updateUser(userInfo);
                    respondDO.setObject(getUserInfo());
                    EventBus.getDefault().post(new EventPostInfo(EventPostInfo.REFRESH_PERSON_INFO_SUCCESS));
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("updateMyData", respondDO.toString());
                        mView.callbackUserInfo(respondDO);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                        RespondDO respondDO = new RespondDO();
                        respondDO.setMsg(GodBaseApplication.getAppContext().getString(R.string.update_failed));
                        respondDO.setStatus(false);
                        mView.callbackUserInfo(respondDO);

                    }
                });
        mCompositeSubscription.add(disposable);
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
//                                User user = new User();
//                                user.setAuth_key(personInfo.getAuth_key());
//                                user.setAvatar(personInfo.getAvatar());
//                                user.setAvatar_thum(personInfo.getAvatar_thum());
//                                user.setBirthday(personInfo.getBirthday());
//                                user.setPhone(personInfo.getPhone());
//                                user.setId(personInfo.getId());
//                                user.setSex(personInfo.getSex());
//                                user.setEmail(personInfo.getEmail());
//                                user.setUsername(personInfo.getUsername());
//                                if(personInfo.getBig_area() != null)
//                                user.setBig_area(personInfo.getBig_area().getId()+"");
//                                if(personInfo.getProvince() != null)
//                                user.setProvince(personInfo.getProvince().getId()+"");
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
        Map<String, String> query = new HashMap<>();
        query.put("m", "customer.uploadImage");
        query.put("auth_key", authKey);
        Disposable disposable = requestStore.get().uploadFile(query, "image", imageFile)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        if (respondDO.isStatus() && !StringUtils.isEmpty(respondDO.getData())) {
                            UploadImage uploadImage = JSON.parseObject(respondDO.getData(), UploadImage.class);
                            respondDO.setObject(uploadImage);
                        }
                    }
                })
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
     *
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


    public String parseJson(String content){

        try {
            org.json.JSONObject jsonObject = new org.json.JSONObject(content);
            return jsonObject.optString("name");
        } catch (Exception e) {
        }

        return content;
    }


    /**
     * 裁剪头像
     *
     * @param activity
     */
    public void cropAvatar(final String photoPath, final FragmentActivity activity) {
        RxPermissions permissions = new RxPermissions(activity);
        Disposable disposable = permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) {
                if (granted) {
                    PhotoUtils.startCorpImage(activity, photoPath, Constant.ActivityReqAndRes.START_CUT_AVATAR);
                }
            }
        });
        mCompositeSubscription.add(disposable);
    }
}
