package com.komutr.client.ui.userRatings;

import android.text.Editable;
import android.text.TextWatcher;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.UserRatingsBinding;
import com.komutr.client.databinding.WalletBinding;
import com.komutr.client.ui.wallet.WalletPresenter;
import com.komutr.client.ui.wallet.WalletView;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.USER_RATINGS, name = "搜索-搜索路线-支付确认-状态-订单详情-用户评分")
public class UserRatingsActivity extends AppBaseActivity<UserRatingsBinding> implements UserRatingsView, TextWatcher {
    @Inject
    UserRatingsPresenter presenter;

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
        setBarTitle(getString(R.string.user_ratings));
        mViewBinding.etUserCommentContent.addTextChangedListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_ratings;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int length = charSequence.toString().length();
        mViewBinding.tvContentLength.setText(length + "/60");
        mViewBinding.btnSubmit.setEnabled(length>0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
