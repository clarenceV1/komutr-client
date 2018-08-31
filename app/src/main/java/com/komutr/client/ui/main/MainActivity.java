package com.komutr.client.ui.main;

import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.databinding.MainBinding;

import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppBaseActivity<MainBinding> implements MainView {
    @Inject
    MainPresenter presenter;

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
        mViewBinding.tvTitle.setText("wo hao le");
    }

    @Override
    public int getLayoutId() {
        return R.layout.main;
    }
}
