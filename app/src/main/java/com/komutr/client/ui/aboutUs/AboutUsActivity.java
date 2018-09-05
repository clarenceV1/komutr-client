package com.komutr.client.ui.aboutUs;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.AboutUs;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.AboutBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_ABOUT_US, name = "我的-关于我们")
public class AboutUsActivity extends AppBaseActivity<AboutBinding> implements AboutUsView {
    @Inject
    AboutUsPresenter presenter;

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
        presenter.requestContent();
    }

    @Override
    public int getLayoutId() {
        return R.layout.about;
    }

    @Override
    public void callback(RespondDO<AboutUs> respondDO) {

    }
}
