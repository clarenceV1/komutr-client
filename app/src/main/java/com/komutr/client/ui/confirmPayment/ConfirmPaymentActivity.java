package com.komutr.client.ui.confirmPayment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.ConfirmPaymentBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.CONFIRM_PAYMENT, name = "搜索-搜索路线-路线详情-确认付款")
public class ConfirmPaymentActivity extends AppBaseActivity<ConfirmPaymentBinding> implements ConfirmPaymentView {
    @Inject
    ConfirmPaymentPresenter presenter;

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
        return R.layout.confirm_payment;
    }

}
