package com.komutr.client.ui.wallet;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.example.clarence.utillibrary.CommonUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.WalletBinding;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_WALLET, name = "我的-我的钱包")
public class WalletActivity extends AppBaseActivity<WalletBinding> implements WalletView, View.OnClickListener {
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
        mViewBinding.ivBack.setOnClickListener(this);
        mViewBinding.tvBill.setOnClickListener(this);
        mViewBinding.btnRecharge.setOnClickListener(this);
        mViewBinding.tvTopFAQ.setOnClickListener(this);

        CommonUtils.setBackground(mViewBinding.tvTopFAQ, CommonUtils.selectorStateColor(this, R.color.white, R.color.color_f1f1f4));
    }

    @Override
    public int getLayoutId() {
        return R.layout.wallet;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvBill://账单

                break;
            case R.id.btnRecharge://充值

                break;
            case R.id.tvTopFAQ://充值
              RouterManager.goFAQ(1);
                break;
        }
    }
}
