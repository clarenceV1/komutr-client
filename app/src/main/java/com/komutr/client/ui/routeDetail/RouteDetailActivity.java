package com.komutr.client.ui.routeDetail;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.RouteDetailBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTE_DETAIL, name = "搜索-搜索路线-路线详情")
public class RouteDetailActivity extends AppBaseActivity<RouteDetailBinding> implements RouteDetailView,View.OnClickListener {

    @Inject
    RouteDetailPresenter presenter;

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
        setBarTitle("Df10001");
        mViewBinding.btnBuy.setOnClickListener(this);
    }


    @Override
    public int getLayoutId() {
        return R.layout.route_detail;
    }

    @Override
    public void onClick(View view) {
        RouterManager.goConfirmPayment();
    }
}
