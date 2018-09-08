package com.komutr.client.ui.Purchase;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.BuySellTicketComment;
import com.komutr.client.been.BuyTicket;
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
public class ReviewPurchaseActivity extends AppBaseActivity<ReviewPurchaseBinding> implements ReviewPurchaseView {

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
        presenter.requestComment();
        mViewBinding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (routes != null) {
                    RoutesShift routesShift = routes.getRoutesShift();
                    RouterStation routerStation = routes.getStation();
                    if (routesShift != null && routerStation != null) {
                        Station beg = routerStation.getBeg();
                        Station end = routerStation.getEnd();
                        if (beg != null && end != null) {
                            presenter.purchaseTicket(routes.getRoute_id(), routesShift.getId(), beg.getId(), end.getId());
                        }
                    }
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.review_purchase;
    }

    @Override
    public void purchaseTicketCallBack(RespondDO<BuyTicket> respondDO) {
        if (respondDO.isStatus()) {
            BuyTicket buyTicket = respondDO.getObject();
            RouterManager.goConfirmPayment(null, buyTicket);
        }
    }

    @Override
    public void commentCallBack(RespondDO<BuySellTicketComment> respondDO) {
        if (respondDO.isStatus() && respondDO.getObject() != null) {
            BuySellTicketComment comment = respondDO.getObject();
            mViewBinding.tvImportantNotes.setText(comment.getContent());
            mViewBinding.tvImportantTitle.setText(comment.getTitle());
        }
    }
}
