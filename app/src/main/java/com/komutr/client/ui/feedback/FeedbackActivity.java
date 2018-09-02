package com.komutr.client.ui.feedback;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.LoginBinding;


import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_FEEDBACK, name = "意见反馈")
public class FeedbackActivity extends AppBaseActivity<LoginBinding> implements FeedbackView {
    @Inject
    FeedbackPresenter presenter;

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
        return R.layout.feedback;
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
