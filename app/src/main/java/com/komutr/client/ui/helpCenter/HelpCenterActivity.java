package com.komutr.client.ui.helpCenter;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.HelpCenter;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.HelpCenterBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_HELP_CENTER, name = "帮助中心")
public class HelpCenterActivity extends AppBaseActivity<HelpCenterBinding> implements HelpCenterView {
    @Inject
    HelpCenterPresenter presenter;
    List<HelpCenter> helpCenters;

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
        setBarTitle(getString(R.string.help_center_title));
        if (titleBarView != null) {
            titleBarView.setRightText(getString(R.string.feed_back));
            titleBarView.setRightClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RouterManager.goFeedback();
                }
            });
        }
        presenter.requestHelpList();
        mViewBinding.tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helpCenters != null && helpCenters.size() > 0) {
                    presenter.requestDetail(helpCenters.get(0).getId());
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.help_center;
    }

    @Override
    public void helpListCallback(RespondDO<List<HelpCenter>> respondDO) {
        if (respondDO.isStatus()) {
            helpCenters = respondDO.getObject();
        }
    }

    @Override
    public void detailCallback(RespondDO respondDO) {

    }
}
