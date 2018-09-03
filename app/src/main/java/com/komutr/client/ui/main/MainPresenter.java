package com.komutr.client.ui.main;

import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.User;

import javax.inject.Inject;

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
}
