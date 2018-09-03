package com.komutr.client.dao;

import android.text.TextUtils;

import com.cai.framework.dagger.ActivityScope;
import com.komutr.client.been.User;

import javax.inject.Inject;

import io.objectbox.Box;

@ActivityScope
public class UserInfoDao extends BaseDAO {
    Box<User> userBox;
    private static User mUser;

    @Inject
    public UserInfoDao() {
        userBox = boxStore.boxFor(User.class);
        switcher();
    }

    public void switcher() {
        mUser = userBox.query().build().findFirst();
    }

    public void saveAndDelete(User user) {
        if (user != null) {
            userBox.removeAll();
            userBox.put(user);
            switcher();
        }
    }

    public boolean isLogin() {
        if (mUser == null) {
            return false;
        }
        return true;
    }

    public long getUserId() {
        if (mUser != null) {
            return mUser.getUserId();
        }
        return 0;
    }

    public String getAppAuth() {
        if (mUser != null) {
            return mUser.getAuth_key();
        }
        return null;
    }

    public User getUser() {
        return mUser;
    }

    /**
     * 更新用户数据
     *
     * @param userInfo
     */
    public void updateUser(User userInfo) {
        if (userInfo == null || mUser == null) {
            return;
        }
        if (!TextUtils.isEmpty(userInfo.getUsername())) {
            mUser.setUsername(userInfo.getUsername());
        }
        if (!TextUtils.isEmpty(userInfo.getAvatar())) {
            mUser.setAvatar(userInfo.getAvatar());
        }
        if (!TextUtils.isEmpty(userInfo.getBig_area())) {
            mUser.setBig_area(userInfo.getBig_area());
        }
        if (!TextUtils.isEmpty(userInfo.getProvince())) {
            mUser.setProvince(userInfo.getProvince());
        }
        if (!TextUtils.isEmpty(userInfo.getSex())) {
            mUser.setSex(userInfo.getSex());
        }
        userBox.put(mUser);
    }

    public void logout() {
        userBox.removeAll();
        mUser = null;
    }
}
