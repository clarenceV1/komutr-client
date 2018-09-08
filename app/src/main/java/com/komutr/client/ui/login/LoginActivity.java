package com.komutr.client.ui.login;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.utils.SMSCountDownTimer;
import com.example.clarence.utillibrary.StringUtils;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.BuildConfig;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.LoginBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_LOGIN, name = "登录/注册")
public class LoginActivity extends AppBaseActivity<LoginBinding> implements LoginView, TextWatcher, View.OnClickListener {
    @Inject
    LoginPresenter presenter;

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
        setBarTitle(getString(R.string.login));
        mViewBinding.etPhone.addTextChangedListener(this);
        mViewBinding.etInputVerificAtionCode.addTextChangedListener(this);
        mViewBinding.btnVerificationCode.setOnClickListener(this);
        mViewBinding.tvServiceAgreement.setOnClickListener(this);
        mViewBinding.btnLogReg.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.login;
    }

    @Override
    public void verificationCodeCallback(RespondDO<PhoneCode> respondDO) {
        mViewBinding.btnVerificationCode.setText(getString(R.string.verification_code));
        mViewBinding.btnVerificationCode.setEnabled(true);
        this.phoneCode = respondDO.getObject();
        if (this.phoneCode != null && !StringUtils.isEmpty(this.phoneCode.getVer_token_key())) {
            new SMSCountDownTimer(mViewBinding.btnVerificationCode, 60000, 1000);
        }
        if (BuildConfig.DEBUG) {
            ToastUtils.showShort("验证码：" + phoneCode.getCode());
        }

    }

    @Override
    public void registeredOrLoginCallBack(RespondDO respondDO) {

        mViewBinding.btnLogReg.setText(getString(R.string.log_in));
        mViewBinding.btnLogReg.setEnabled(true);
        if (respondDO.getFromCallBack() != -1) {
            ToastUtils.showShort(respondDO.getMsg());
            if (respondDO.isStatus()) { //成功
                finish();
            } else {//失败

            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String text = charSequence.toString();
        if (mViewBinding.etPhone.hasFocus()) {//获得焦点时为手机号的输入项,获取验证码按钮可点击
            boolean isNotEmpty = !StringUtils.isEmpty(text);
            mViewBinding.btnVerificationCode.setEnabled(isNotEmpty);
            mViewBinding.btnLogReg.setEnabled(isNotEmpty && !StringUtils.isEmpty(StringUtils.getString(mViewBinding.etInputVerificAtionCode)));
        } else if (mViewBinding.etInputVerificAtionCode.hasFocus()) {//获得焦点时为获取验证码的输入项，登录按钮可点击
            boolean isNotEmpty = !StringUtils.isEmpty(text);
            mViewBinding.btnLogReg.setEnabled(isNotEmpty && !StringUtils.isEmpty(StringUtils.getString(mViewBinding.etPhone)));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvServiceAgreement) {//注册协议

        } else {
            String phone = mViewBinding.etPhone.getText().toString();
            if (phone.length() != 11) {
                ToastUtils.showShort(getString(R.string.please_input_correct_phone_number));
                return;
            }
            switch (view.getId()) {
                case R.id.btnVerificationCode://获取验证码
                    view.setTag(phone);
                    mViewBinding.btnVerificationCode.setEnabled(false);
                    mViewBinding.btnVerificationCode.setText(getString(R.string.getting));
                    presenter.verificationCode(phone);
                    break;
                case R.id.btnLogReg://登录
                    if(StringUtils.isEmpty(StringUtils.getString(mViewBinding.etInputVerificAtionCode))){
                        ToastUtils.showShort(getString(R.string.please_input_correct_phone_number));
                        return;
                    }
                    if (this.phoneCode == null || StringUtils.isEmpty(this.phoneCode.getVer_token_key())) {//为获取验证码
                        ToastUtils.showShort(getString(R.string.not_get_code));
                        return;
                    }
                    String getCodePhone = (String) mViewBinding.btnVerificationCode.getTag();
                    if (!phone.equals(getCodePhone)) {//当前手机号与获取验证码的手机号不一致
                        ToastUtils.showShort(getString(R.string.please_input_correct_phone_number));
                        return;
                    }
                    mViewBinding.btnVerificationCode.setEnabled(false);
                    mViewBinding.btnVerificationCode.setText(getString(R.string.binding_in));
                    presenter.registeredOrLogin(StringUtils.getString(mViewBinding.etInputVerificAtionCode), phone, this.phoneCode.getVer_token_key());
                    break;
            }
        }

    }


}
