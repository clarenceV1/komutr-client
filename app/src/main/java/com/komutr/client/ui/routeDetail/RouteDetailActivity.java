package com.komutr.client.ui.routeDetail;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.google.android.gms.maps.SupportMapFragment;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.RoutesInfo;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.RouteDetailBinding;
import com.komutr.client.ui.main.fragment.book.MapHelp;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTE_DETAIL, name = "搜索-搜索路线-路线详情")
public class RouteDetailActivity extends AppBaseActivity<RouteDetailBinding> implements RouteDetailView, View.OnClickListener {

    @Inject
    RouteDetailPresenter presenter;
    @Autowired(name = "routeId")
    String routeId;
    @Autowired(name = "shiftId")
    String shiftId;
    MapHelp mapHelp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

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
        initMap();
        presenter.routesInfo(routeId, shiftId);
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapHelp = new MapHelp(this, mapFragment);
    }

    @Override
    public int getLayoutId() {
        return R.layout.route_detail;
    }

    @Override
    public void onClick(View view) {
        RouterManager.goConfirmPayment(null, null);
    }

    @Override
    public void routesInfoCallback(RespondDO<RoutesInfo> respondDO) {
        if (respondDO.isStatus() && respondDO.getObject() != null && mapHelp != null) {
            mapHelp.drawRoutes(respondDO.getObject());
        }
    }
}
