package com.komutr.client.ui.email;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.utils.SMSCountDownTimer;
import com.example.clarence.utillibrary.StringUtils;
import com.example.clarence.utillibrary.ToastUtils;
import com.example.clarence.utillibrary.ValidatorUtil;
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
public class BindEmailActivity extends AppBaseActivity<BindEmailBinding> implements BindEmailView, TextWatcher, View.OnClickListener {
    @Inject
    BindEmailPresenter presenter;
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
        setBarTitle(getString(R.string.bind_email_title));

        mViewBinding.etEmail.addTextChangedListener(this);
        mViewBinding.etEmailCode.addTextChangedListener(this);
        mViewBinding.btnEmailCode.setOnClickListener(this);
        mViewBinding.btnBinding.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.bind_email;
    }

    @Override
    public void checkEmail(RespondDO<PhoneCode> respondDO) {
        mViewBinding.btnEmailCode.setText(getString(R.string.verification_code));
        mViewBinding.btnEmailCode.setEnabled(true);
        if (respondDO.isStatus()) {
            this.phoneCode = respondDO.getObject();
            if (this.phoneCode != null && !StringUtils.isEmpty(this.phoneCode.getVer_token_key())) {
                new SMSCountDownTimer(mViewBinding.btnEmailCode, 60000, 1000);
            }
        }else {
            ToastUtils.showShort(respondDO.getMsg());
        }


    }

    @Override
    public void bindEmailCallback(RespondDO respondDO) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String text = charSequence.toString();
        if (mViewBinding.etEmailCode.hasFocus()) {//获得焦点时为手机号的输入项,获取验证码按钮可点击
            boolean isNotEmpty = !StringUtils.isEmpty(text);
            mViewBinding.btnEmailCode.setEnabled(isNotEmpty);
            mViewBinding.btnBinding.setEnabled(isNotEmpty && !StringUtils.isEmpty(StringUtils.getString(mViewBinding.etEmail)));
        } else if (mViewBinding.etEmail.hasFocus()) {//获得焦点时为获取验证码的输入项，登录按钮可点击
            boolean isNotEmpty = !StringUtils.isEmpty(text);
            mViewBinding.btnBinding.setEnabled(isNotEmpty && !StringUtils.isEmpty(StringUtils.getString(mViewBinding.etEmailCode)));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {

        String email = StringUtils.getString(mViewBinding.etEmail);
        if (ValidatorUtil.isEmail(email)) {
            ToastUtils.showShort(getString(R.string.please_enter_your_vaild_email));
            return;
        }
        switch (view.getId()) {
            case R.id.etEmailCode://获取验证码
                view.setTag(email);
                mViewBinding.btnEmailCode.setEnabled(false);
                mViewBinding.btnEmailCode.setText(getString(R.string.getting));
                presenter.checkEmail(email);
                break;
            case R.id.btnLogReg://登录

                if (this.phoneCode == null || StringUtils.isEmpty(this.phoneCode.getVer_token_key())) {//为获取验证码
                    ToastUtils.showShort(getString(R.string.not_get_email_code));
                    return;
                }
                String getCodeEmail = (String) mViewBinding.etEmailCode.getTag();
                if (!email.equals(getCodeEmail)) {//当前邮箱与获取验证码的邮箱不一致
                    ToastUtils.showShort(getString(R.string.please_input_correct_email_address));
                    return;
                }
                mViewBinding.btnEmailCode.setEnabled(false);
                mViewBinding.btnEmailCode.setText(getString(R.string.binding_in));
                presenter.bindEmail(StringUtils.getString(mViewBinding.etEmailCode), email, this.phoneCode);
                break;
        }


    }
}
