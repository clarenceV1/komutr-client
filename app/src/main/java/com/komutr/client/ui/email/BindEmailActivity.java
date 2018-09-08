package com.komutr.client.ui.email;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.BindEmailBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_BIND_EMAIL, name = "绑定邮箱")
public class BindEmailActivity extends AppBaseActivity<BindEmailBinding> implements BindEmailView {
    @Inject
    BindEmailPresenter presenter;
    PhoneCode phoneCode;
    String email;

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
        setBarTitle(getString(R.string.bind_email_title));
        email = "763287516@qq.com";
        presenter.checkEmail(email);
       /* mViewBinding.tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = phoneCode.getCode();//测试的时候才可以这样获取code
                presenter.bindEmail(email, code, phoneCode);
            }
        });*/
    }

    @Override
    public int getLayoutId() {
        return R.layout.bind_email;
    }

    @Override
    public void checkEmail(RespondDO<PhoneCode> respondDO) {
        if (respondDO.isStatus()) {
            phoneCode = respondDO.getObject();
        }
    }

    @Override
    public void bindEmailCallback(RespondDO respondDO) {

    }
}
