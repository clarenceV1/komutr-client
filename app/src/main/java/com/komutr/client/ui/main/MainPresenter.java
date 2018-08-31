package com.komutr.client.ui.main;

import com.komutr.client.base.AppBasePresenter;

import javax.inject.Inject;

public class MainPresenter extends AppBasePresenter<MainView> {

    @Inject
    public MainPresenter() {

    }

    @Override
    public void onAttached() {
        mView.tast("ssss");
    }
    public String getTest2(){
        return "sss";
    }
}
