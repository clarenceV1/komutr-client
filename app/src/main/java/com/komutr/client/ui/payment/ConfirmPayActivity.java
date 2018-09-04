package com.komutr.client.ui.payment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.ConfirmPayBinding;
import com.komutr.client.ui.wallet.WalletPresenter;
import com.komutr.client.ui.wallet.WalletView;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_CONFIRM_PAY, name = "我的-扫一扫-填写金额")
public class ConfirmPayActivity extends AppBaseActivity<ConfirmPayBinding> implements WalletView {
    @Inject
    WalletPresenter presenter;

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
        return R.layout.confirm_pay;
    }

}
