package com.komutr.client.dao;


import com.cai.framework.dagger.ActivityScope;
import com.example.clarence.utillibrary.StringUtils;
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
        if (!StringUtils.isEmpty(userInfo.getUsername())) {
            mUser.setUsername(userInfo.getUsername());
        }
        if (!StringUtils.isEmpty(userInfo.getAvatar())) {
            mUser.setAvatar(userInfo.getAvatar());
        }
        if (!StringUtils.isEmpty(userInfo.getBig_area())) {
            mUser.setBig_area(userInfo.getBig_area());
        }
        if (!StringUtils.isEmpty(userInfo.getProvince())) {
            mUser.setProvince(userInfo.getProvince());
        }
        if (!StringUtils.isEmpty(userInfo.getSex())) {
            mUser.setSex(userInfo.getSex());
        }
        if (!StringUtils.isEmpty(userInfo.getEmail())) {
            mUser.setEmail(userInfo.getEmail());
        }
        if (!StringUtils.isEmpty(userInfo.getPhone())) {
            mUser.setPhone(userInfo.getPhone());
        }
        if (!StringUtils.isEmpty(userInfo.getAvatar_thum())) {
            mUser.setAvatar_thum(userInfo.getAvatar_thum());
        }
        if (!StringUtils.isEmpty(userInfo.getAuth_key())) {
            mUser.setAuth_key(userInfo.getAuth_key());
        }
        if (!StringUtils.isEmpty(userInfo.getBirthday())) {
            mUser.setBirthday(userInfo.getBirthday());
        }
        
        userBox.put(mUser);
    }

    public void logout() {
        userBox.removeAll();
        mUser = null;
    }
}
