package com.komutr.client.ui.confirmPayment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.BuyTicket;
import com.komutr.client.common.RouterManager;
import com.komutr.client.dagger.component.DaggerCommonComponent;
import com.komutr.client.databinding.ConfirmPaymentBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.CONFIRM_PAYMENT, name = "搜索-搜索路线-路线详情-确认付款")
public class ConfirmPaymentActivity extends AppBaseActivity<ConfirmPaymentBinding> implements ConfirmPaymentView, View.OnClickListener {
    @Autowired(name = "scanContent")
    String scanContent;//扫描二维码内容
    @Inject
    ConfirmPaymentPresenter presenter;
    @Autowired(name = "BuyTicket")
    BuyTicket buyTicket;

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
        buyTicket = getArouterSerializableData("BuyTicket");
        if (!TextUtils.isEmpty(scanContent)) {
            mViewBinding.llScanPayLayout.setVisibility(View.VISIBLE);
            mViewBinding.tvPaymentMethod.setVisibility(View.VISIBLE);
            mViewBinding.tvPaymentMethod.setVisibility(View.VISIBLE);
            mViewBinding.viewScanPay.setVisibility(View.VISIBLE);
            mViewBinding.tvPaymentMethodOne.setVisibility(View.GONE);
            mViewBinding.btnPay.setText(getString(R.string.confirm_payment));
        } else if (buyTicket != null) {
            mViewBinding.tvBusPrice.setText(buyTicket.getAmount());
        }

        mViewBinding.btnPay.setOnClickListener(this);
        mViewBinding.ivClosePage.setOnClickListener(this);

    }

    @Override
    public int getLayoutId() {
        return R.layout.confirm_payment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPay://支付
                RouterManager.goPayStatus();
                break;
            case R.id.ivClosePage:
                finish();
                break;
        }

    }
}
