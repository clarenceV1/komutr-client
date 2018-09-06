package com.komutr.client.ui.region;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.RegionBinding;

import java.util.List;

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
        setBarTitle(getString(R.string.region_title));
        mViewBinding.tvTest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.requestBigArea();
//                        presenter.requestNextArea();
                    }
                });
    }

    @Override
    public int getLayoutId() {
        return R.layout.region;
    }

    @Override
    public void bigAreaCallback(RespondDO respondDO) {

    }

    @Override
    public void nextAreaCallback(RespondDO respondDO) {

    }
}
