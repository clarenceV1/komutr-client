package com.komutr.client.ui.feedback;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.Constant;
import com.komutr.client.databinding.LoginBinding;
import com.komutr.client.ui.login.LoginView;

import java.util.List;

import javax.inject.Inject;

@Route(path = Constant.ROUTER_FEEDBACK, name = "意见反馈")
public class FeedbackActivity extends AppBaseActivity<LoginBinding> implements LoginView {
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
}
