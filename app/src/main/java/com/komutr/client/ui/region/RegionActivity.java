package com.komutr.client.ui.region;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.RegionBinding;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_REGION, name = "我的-个人信息-地区")
public class RegionActivity extends AppBaseActivity<RegionBinding> implements RegionView {
    @Inject
    RegionPresenter presenter;

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initView() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.region;
    }

    @Override
    public Map<String, String> getParams(int requestFlag) {
        return null;
    }

    @Override
    public void onBegin() {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void showToastMsg(String msg) {

    }
}
