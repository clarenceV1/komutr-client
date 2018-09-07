package com.komutr.client.ui.position;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.PositionBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.POSITION, name = "搜索位置")
public class PositionActivity extends AppBaseActivity<PositionBinding> implements PositionView {
    @Inject
    PositionPresenter presenter;

    @Autowired(name = "isStartPosition")
    boolean isStartPosition;//起点站

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
        return R.layout.position;
    }

}
