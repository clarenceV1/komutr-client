package com.komutr.client.ui.bill.detail;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.BillDetailBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_WALLET, name = "扫码支付-账单详情/我的-退票账单详情/我的-我的钱包-充值账单详情")
public class BillDetailActivity extends AppBaseActivity<BillDetailBinding> implements BillDetailView {
    @Inject
    BillDetailPresenter presenter;


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
       presenter.requestBillDetail();
    }

    @Override
    public int getLayoutId() {
        return R.layout.bill_detail;
    }

}
