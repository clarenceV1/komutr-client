package com.komutr.client.ui.Purchase;

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
import com.komutr.client.been.RoutesShift;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.been.Station;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.ReviewPurchaseBinding;

import java.util.List;

import javax.inject.Inject;



@Route(path = RouterManager.REVIEW_PURCHASE, name = "搜索-搜索路线-查看购买")
public class ReviewPurchaseActivity extends AppBaseActivity<ReviewPurchaseBinding> implements ReviewPurchaseView,View.OnClickListener {

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
        RoutesShift routesShift = routes.getRoutesShift();//班次绝对不会空 放心
        mViewBinding.tvSinglePrice.setText("₱" +(routes.getTicket() != null ? routes.getTicket().getPrice() : getString(R.string.default_amount)));
        mViewBinding.tvBusNumber.setText(routes.getRoute_id());
        mViewBinding.tvTotalPrice.setText(mViewBinding.tvSinglePrice.getText().toString());
        if(routesShift != null){
            String time = routesShift.getBeg_time_int();
            if (!StringUtils.isEmpty(time) && time.length() == 4) {
                boolean isAm = false;
                if (Integer.valueOf(time.substring(0, 2)) < 12) {
                    isAm = true;
                }
                String[] ams = getString(R.string.am_pm).split(",");
                time = time.substring(0, 2) + ":" + time.substring(2, 4);
                mViewBinding.tvMyTripsTime.setText(time + (isAm ? ams[0] : ams[1]));
                mViewBinding.tvMyTripsDate.setText(DateUtils.formatDate(System.currentTimeMillis(),"dd/MM/yyyy"));
            }
        }


        RouterStation routerStation = routes.getStation();
        if (routerStation != null) {
            if (routerStation.getBeg() != null) {
                mViewBinding.tvStartLocation.setText(routerStation.getBeg().getName());
            }
            if (routerStation.getEnd() != null) {
                mViewBinding.tvEndLocation.setText(routerStation.getEnd().getName());
            }
        }

        Chauffeur chauffeur = routesShift.getChauffeur();
        if (chauffeur != null) {
            /*if (!StringUtils.isEmpty(chauffeur.getAvatar())) {
                String icon = Constant.OFFICIAL_BASE_URL.substring(0, Constant.OFFICIAL_BASE_URL.length() - 1) + chauffeur.getAvatar_thum();
                ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                        .url(icon)
                        .error(R.drawable.default_avatar)
                        .placeholder(R.drawable.default_avatar)
                        .transformation(new GlideCircleTransform(this))
                        .build();
                imageParams.setImageView(mViewBinding.ivDriverAvatar);
                iLoadImage.loadImage(context, imageParams);
            }*/
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
            if(refund != null){
                mViewBinding.tvImportantNotes.setText(refund.getContent());
                mViewBinding.tvImportantTitle.setText(refund.getTitle());
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (routes != null) {
            RoutesShift routesShift = routes.getRoutesShift();
            RouterStation routerStation = routes.getStation();
            if (routesShift != null && routerStation != null) {
                showDialog(getString(R.string.buying),true);
                Station beg = routerStation.getBeg();
                Station end = routerStation.getEnd();
                if (beg != null && end != null) {
                    presenter.purchaseTicket(routes.getRoute_id(), routesShift.getId(), beg.getId(), end.getId());
                }
            }
        }
    }
}
