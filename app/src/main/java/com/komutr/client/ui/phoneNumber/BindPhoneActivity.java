package com.komutr.client.ui.phoneNumber;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.User;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.BindPhoneBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_BIND_PHONE, name = "我的-设置-更换手机号码")
public class BindPhoneActivity extends AppBaseActivity<BindPhoneBinding> implements BindPhoneView {
    @Inject
    PhoneNumberPresenter presenter;

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
        setBarTitle(getString(R.string.bind_phone_number_title));
        User user = presenter.getInfo();
        if (user != null) {
            mViewBinding.tvUserPhone.setText(user.getPhone());
        }
        mViewBinding.tvBind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.goPhoneNumber();
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.bind_phone;
    }

}
