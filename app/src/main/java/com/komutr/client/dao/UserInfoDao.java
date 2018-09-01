package com.komutr.client.dao;

import com.cai.framework.dagger.ActivityScope;
import com.komutr.client.been.UserInfo;

import javax.inject.Inject;

import io.objectbox.Box;

@ActivityScope
public class UserInfoDao extends BaseDAO{
    Box<UserInfo> userBox;
    private static UserInfo mUser;

    @Inject
    public UserInfoDao() {
        userBox = boxStore.boxFor(UserInfo.class);
        switcher();
    }

    public void switcher() {
        mUser = userBox.query().build().findFirst();
    }

    public void saveAndDelete(UserInfo user) {
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
            return mUser.getUser_id();
        }
        return 0;
    }

    public String getAppAuth(){
        if (mUser != null) {
            return mUser.getApp_auth();
        }
        return null;
    }
}
