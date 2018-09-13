package com.komutr.client.ui.purchase;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.example.clarence.utillibrary.DateUtils;
import com.example.clarence.utillibrary.StringUtils;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.BuySellTicketDetails;
import com.komutr.client.been.BuyTicket;
import com.komutr.client.been.Chauffeur;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.RouterStation;
import com.komutr.client.been.Routes;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.been.Station;
import com.komutr.client.been.Ticket;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.ReviewPurchaseBinding;

import java.util.List;

import javax.inject.Inject;


@Route(path = RouterManager.REVIEW_PURCHASE, name = "搜索-搜索路线-查看购买")
public class ReviewPurchaseActivity extends AppBaseActivity<ReviewPurchaseBinding> implements ReviewPurchaseView, View.OnClickListener {

    @Autowired(name = "Routes")
    SearchRoutes routes;//序列号的对象没办法自动解析，需要getArouterSerializableData

    @Inject
    ReviewPurchasePresenter presenter;

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
        setBarTitle(getString(R.string.review_purchase));
        routes = getArouterSerializableData("Routes");

        mViewBinding.btnConfirm.setOnClickListener(this);
        presenter.requestBuySellTicketDetails();
        initData();

    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (routes == null) {
            return;
        }
        mViewBinding.tvTotalPrice.setText(mViewBinding.tvSinglePrice.getText().toString());
        String time = routes.getBeg_time_int();
        if (!StringUtils.isEmpty(time) && time.length() == 4) {
            boolean isAm = false;
            if (Integer.valueOf(time.substring(0, 2)) < 12) {
                isAm = true;
            }
            String[] ams = getString(R.string.am_pm).split(",");
            time = time.substring(0, 2) + ":" + time.substring(2, 4);
            mViewBinding.tvMyTripsTime.setText(time + (isAm ? ams[0] : ams[1]));
            mViewBinding.tvMyTripsDate.setText(DateUtils.formatDate(System.currentTimeMillis(), "dd/MM/yyyy"));
        }

        if (routes.getRoute() != null && routes.getRoute().getStation() != null) {
            Routes route = routes.getRoute();
            if (route.getTicket() != null) {
                Ticket ticket = route.getTicket();
                mViewBinding.tvSinglePrice.setText("₱" + (ticket != null ? ticket.getPrice() : getString(R.string.default_amount)));
            }
            mViewBinding.tvBusNumber.setText(route.getRoute_id());

            RouterStation routerStation = routes.getRoute().getStation();
            if (routerStation.getBeg() != null) {
                mViewBinding.tvStartLocation.setText(routerStation.getBeg().getName());
            }
            if (routerStation.getEnd() != null) {
                mViewBinding.tvEndLocation.setText(routerStation.getEnd().getName());
            }
        }

        Chauffeur chauffeur = routes.getChauffeur();
        if (chauffeur != null) {
            mViewBinding.tvLicensePlateNo.setText(chauffeur.getLicence_plate());
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.review_purchase;
    }

    @Override
    public void purchaseTicketCallBack(RespondDO<BuyTicket> respondDO) {
        hiddenDialog();
        ToastUtils.showShort(respondDO.getMsg());
        if (respondDO.isStatus()) {
            BuyTicket buyTicket = respondDO.getObject();
            RouterManager.goConfirmPayment(null, buyTicket);
        }
    }

    @Override
    public void buySellTicketDetailsCallBack(RespondDO<BuySellTicketDetails> respondDO) {
        if (respondDO.isStatus() && respondDO.getObject() != null) {
            BuySellTicketDetails buySellTicketDetails = respondDO.getObject();
            BuySellTicketDetails.Refund refund = buySellTicketDetails.getRefund();
            if (refund != null) {
                mViewBinding.tvImportantNotes.setText(refund.getContent());
                mViewBinding.tvImportantTitle.setText(refund.getTitle());
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (routes != null) {
            if (routes.getRoute() != null && routes.getRoute().getStation() != null) {
                Routes temR = routes.getRoute();
                RouterStation station = temR.getStation();
                showDialog(getString(R.string.buying), true);
                Station beg = station.getBeg();
                Station end = station.getEnd();
                if (beg != null && end != null) {
                    presenter.purchaseTicket(temR.getRoute_id(), routes.getId(), beg.getId(), end.getId());
                }
            }
        }
    }
}
