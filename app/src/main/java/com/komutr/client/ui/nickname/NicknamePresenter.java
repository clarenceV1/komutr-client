package com.komutr.client.ui.nickname;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.logger.Logger;
import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.User;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class NicknamePresenter extends AppBasePresenter<NicknameView> {

    @Inject
    public NicknamePresenter() {

    }

    @Override
    public void onAttached() {
    }

    /**
     * 验证用户名是否可用
     *
     * @param username
     */
    public void checkUsername(final String username) {
        String auth_key = userInfoDao.get().getAppAuth();
        Map<String, Object> query = new HashMap<>();
        query.put("m", "customer.checkUsername");
        query.put("auth_key", auth_key);
        query.put("username", username);
        final Disposable disposable = requestStore.get().commonRequest(query)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("checkUsername", respondDO.toString());
                        if (respondDO.isStatus()) { //成功
                            mView.checkUsername(respondDO);
                        } else {//失败
                            mView.checkUsername(respondDO);
                        }
                        updateMyData(username, null, -1, -1, -1);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });
        mCompositeSubscription.add(disposable);
    }

    /**
     * 更新用户信息
     *
     * @param avatar   先上传图片 在提交
     * @param username 用户名提交时要检查是否存在
     * @param big_area 大区id
     * @param province 省id
     * @param sex      性别 1男 2女
     */
    public void updateMyData(String username, String avatar, int big_area, int province, int sex) {
        Map<String, Object> query = new HashMap<>();
        String auth_key = userInfoDao.get().getAppAuth();
        query.put("m", "customer.updateMyData");
        query.put("auth_key", auth_key);
        if (!TextUtils.isEmpty(avatar)) {
            query.put("avatar", avatar);
        }
        if (!TextUtils.isEmpty(username)) {
            query.put("username", username);
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
                if (respondDO.isStatus() && !TextUtils.isEmpty(respondDO.getData())) {
                    User userInfo = JSON.parseObject(respondDO.getData(), User.class);
                    userInfoDao.get().updateUser(userInfo);
                    respondDO.setObject(userInfo);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RespondDO>() {
                    @Override
                    public void accept(RespondDO respondDO) {
                        Log.d("updateMyData", respondDO.toString());
                        if (respondDO.isStatus()) { //成功
                            mView.updateMyData(respondDO);
                        } else {//失败
                            mView.updateMyData(respondDO);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Logger.e(throwable.getMessage());
                    }
                });
        mCompositeSubscription.add(disposable);
    }
}
