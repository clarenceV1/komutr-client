package com.komutr.client.ui.Purchase;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.ReviewPurchaseBinding;
import com.komutr.client.databinding.WalletBinding;
import com.komutr.client.ui.wallet.WalletPresenter;
import com.komutr.client.ui.wallet.WalletView;

import java.io.Serializable;
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
        if (routes != null) {
            presenter.purchaseTicket(routes.getRouteId(), routes.getShiftId(), routes.getBegStation(), routes.getEndStation());
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.review_purchase;
    }

    @Override
    public void purchaseTicketCallBack(RespondDO respondDO) {

    }
}
