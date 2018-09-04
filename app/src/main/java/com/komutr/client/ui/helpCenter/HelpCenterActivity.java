package com.komutr.client.ui.helpCenter;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.HelpCenterBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_HELP_CENTER, name = "帮助中心")
public class HelpCenterActivity extends AppBaseActivity<HelpCenterBinding> implements HelpCenterView {
    @Inject
    HelpCenterPresenter presenter;

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
        presenter.requestHelpCenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.help_center;
    }

    @Override
    public void callback(RespondDO respondDO) {

    }
}
