package com.komutr.client.ui.main;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.annotation.apt.Router;
import com.cai.framework.base.GodBasePresenter;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.MainBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_MAIN, name = "首页")
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
        mViewBinding.tvTitle.setText(presenter.getTest2());
        mViewBinding.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.goWeb("http://www.baidu.com","百度是我孙子",null);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.main;
    }

    @Override
    public void tast(String msg) {
        ToastUtils.showShort(msg);
    }
}
