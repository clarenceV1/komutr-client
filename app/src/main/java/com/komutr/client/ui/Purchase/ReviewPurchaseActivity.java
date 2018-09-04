package com.komutr.client.ui.Purchase;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.ReviewPurchaseBinding;
import com.komutr.client.databinding.WalletBinding;
import com.komutr.client.ui.wallet.WalletPresenter;
import com.komutr.client.ui.wallet.WalletView;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.REVIEW_PURCHASE, name = "搜索-搜索路线-查看购买")
public class ReviewPurchaseActivity extends AppBaseActivity<ReviewPurchaseBinding> implements ReviewPurchaseView {

    @Inject
    ReviewPurchasePresenter presenter;

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
    }

    @Override
    public int getLayoutId() {
        return R.layout.review_purchase;
    }

}
