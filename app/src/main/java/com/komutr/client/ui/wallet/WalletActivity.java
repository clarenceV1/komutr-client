package com.komutr.client.ui.wallet;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.example.clarence.utillibrary.CommonUtils;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Wallet;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.WalletBinding;

import java.util.List;

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
        setBarTitle(getString(R.string.komutr_pay));
        if (titleBarView != null) {
            titleBarView.setRightText(getString(R.string.bill));
            titleBarView.setRightClickListener(this);
        }
        mViewBinding.btnRecharge.setOnClickListener(this);
        mViewBinding.tvTopFAQ.setOnClickListener(this);

        CommonUtils.setBackground(mViewBinding.tvTopFAQ, CommonUtils.selectorStateColor(this, R.color.white, R.color.color_f1f1f4));
        showDialog(getString(R.string.please_wait), true);
        presenter.requestWallet();

    }

    @Override
    public int getLayoutId() {
        return R.layout.wallet;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case com.cai.framework.R.id.tvRight://账单
                RouterManager.goBill();
                break;
            case R.id.btnRecharge://充值
                RouterManager.goRecharge();
                break;
            case R.id.tvTopFAQ://充值
                RouterManager.goFAQ(1);
                break;
        }
    }

    @Override
    public void walletCallBack(RespondDO<Wallet> respondDO) {
        hiddenDialog();
        if (respondDO.isStatus()) {
            Wallet wallet = respondDO.getObject();
            if (wallet != null) {
                mViewBinding.tvBalance.setText(wallet.getBalance());
            }
        } else {
            ToastUtils.showShort(respondDO.getMsg());
        }


    }

}
