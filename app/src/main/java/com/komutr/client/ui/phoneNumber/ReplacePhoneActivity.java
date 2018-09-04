package com.komutr.client.ui.phoneNumber;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.ReplacePhoneBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_REPLACE_PHONE, name = "我的-设置-重新绑定")
public class ReplacePhoneActivity extends AppBaseActivity<ReplacePhoneBinding> implements ReplacePhoneView {
    @Inject
    ReplacePhonePresenter presenter;
    PhoneCode phoneCode;

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
        setBarTitle(getString(R.string.replace_the_binding_title));
        mViewBinding.btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mViewBinding.tvPhone.getText().toString();
                presenter.verificationCode(phone);
            }
        });
        mViewBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneCode!=null){
                    String phone = mViewBinding.tvPhone.getText().toString();
                    presenter.changePhoneNumber(phone,phoneCode);
                }

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.replace_phone;
    }

    @Override
    public void verificationCodeCallback(RespondDO<PhoneCode> respondDO) {
        if(respondDO.isStatus()){//成功失败
            phoneCode = respondDO.getObject();
        }else{//失败

        }

    }

    @Override
    public void changePhoneNumberCallback(RespondDO respondDO) {
        if(respondDO.isStatus()){//成功失败
           
        }else{//失败

        }
    }
}
