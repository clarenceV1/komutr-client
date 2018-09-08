package com.komutr.client.ui.phoneNumber;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.utils.SMSCountDownTimer;
import com.example.clarence.utillibrary.StringUtils;
import com.example.clarence.utillibrary.ToastUtils;
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
public class ReplacePhoneActivity extends AppBaseActivity<ReplacePhoneBinding> implements ReplacePhoneView, TextWatcher, View.OnClickListener {
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


        mViewBinding.etPhone.addTextChangedListener(this);
        mViewBinding.etEnterConfirmationCode.addTextChangedListener(this);
        mViewBinding.etInputVerificAtionCode.addTextChangedListener(this);
        mViewBinding.btnBinding.setOnClickListener(this);
        mViewBinding.btnGetCode.setOnClickListener(this);
        mViewBinding.btnVerificationCode.setOnClickListener(this);
       /* mViewBinding.btnCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = mViewBinding.tvPhone.getText().toString();
                presenter.verificationCode(phone);
            }
        });
        mViewBinding.btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneCode != null) {
                    String phone = mViewBinding.tvPhone.getText().toString();
                    String code = phoneCode.getCode();//测试的时候才能这样用
                    presenter.changePhoneNumber(phone, code, phoneCode);
                }

            }
        });*/
    }

    @Override
    public int getLayoutId() {
        return R.layout.replace_phone;
    }


    @Override
    public void verificationCodeCallback(RespondDO<PhoneCode> respondDO) {

        if (respondDO.getFromCallBack() == 1) {//旧手机验证码
            mViewBinding.btnGetCode.setText(getString(R.string.get_code));
            mViewBinding.btnGetCode.setEnabled(true);
        } else {
            mViewBinding.btnVerificationCode.setText(getString(R.string.get_code));
            mViewBinding.btnVerificationCode.setEnabled(true);
        }


        if (respondDO.isStatus()) {//成功
            PhoneCode phoneCode = respondDO.getObject();
            if (phoneCode != null && !StringUtils.isEmpty(phoneCode.getVer_token_key())) {
                new SMSCountDownTimer(respondDO.getFromCallBack() == 1 ? mViewBinding.btnGetCode : mViewBinding.btnVerificationCode, 60000, 1000);
                if(respondDO.getFromCallBack() == 2){
                    this.phoneCode = phoneCode;
                }
            }
        } else {//失败

        }

    }

    @Override
    public void changePhoneNumberCallback(RespondDO respondDO) {

        ToastUtils.showShort(respondDO.getMsg());
        if (respondDO.isStatus()) {//成功
          finish();
        } else {//失败

        }
    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String text = charSequence.toString();
        if (mViewBinding.etEnterConfirmationCode.hasFocus()) {//获取旧手机验证码
            boolean isNotEmpty = !StringUtils.isEmpty(text);
            mViewBinding.btnGetCode.setEnabled(isNotEmpty);
            mViewBinding.btnVerificationCode.setEnabled(!StringUtils.isEmpty(StringUtils.getString(mViewBinding.etPhone)));
            mViewBinding.btnBinding.setEnabled(isNotEmpty && !StringUtils.isEmpty(StringUtils.getString(mViewBinding.etPhone)) && !StringUtils.isEmpty(StringUtils.getString(mViewBinding.etInputVerificAtionCode)));
        } else if (mViewBinding.etPhone.hasFocus()) {//获得焦点时为手机号的输入项,获取验证码按钮可点击
            boolean isNotEmpty = !StringUtils.isEmpty(text);
            mViewBinding.btnVerificationCode.setEnabled(isNotEmpty);
            mViewBinding.btnGetCode.setEnabled(!StringUtils.isEmpty(StringUtils.getString(mViewBinding.etEnterConfirmationCode)));
            mViewBinding.btnBinding.setEnabled(isNotEmpty && !StringUtils.isEmpty(StringUtils.getString(mViewBinding.etInputVerificAtionCode)) && !StringUtils.isEmpty(StringUtils.getString(mViewBinding.etEnterConfirmationCode)));
        } else if (mViewBinding.etInputVerificAtionCode.hasFocus()) {//获得焦点时为获取验证码的输入项，登录按钮可点击
            boolean isNotEmpty = !StringUtils.isEmpty(text);
            mViewBinding.btnBinding.setEnabled(isNotEmpty && !StringUtils.isEmpty(StringUtils.getString(mViewBinding.etPhone)) && !StringUtils.isEmpty(StringUtils.getString(mViewBinding.etEnterConfirmationCode)));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGetCode://获取旧手机验证码
                String phone = mViewBinding.etEnterConfirmationCode.getText().toString();
                if (phone.length() != 11) {
                    ToastUtils.showShort(getString(R.string.please_input_correct_phone_number));
                    return;
                }
                mViewBinding.btnGetCode.setTag(phone);
                mViewBinding.btnGetCode.setEnabled(false);
                mViewBinding.btnGetCode.setText(getString(R.string.getting));
                presenter.verificationCode(phone, 1);
                break;
            case R.id.btnVerificationCode://获取新手机验证码
                String newPhone = mViewBinding.etPhone.getText().toString();
                if (newPhone.length() != 11) {
                    ToastUtils.showShort(getString(R.string.please_input_correct_phone_number));
                    return;
                }
                mViewBinding.btnVerificationCode.setTag(newPhone);
                mViewBinding.btnVerificationCode.setEnabled(false);
                mViewBinding.btnVerificationCode.setText(getString(R.string.getting));
                presenter.verificationCode(newPhone, 2);
                break;
            case R.id.btnBinding://更换手机
                if (this.phoneCode == null || StringUtils.isEmpty(this.phoneCode.getVer_token_key())) {//为获取验证码
                    ToastUtils.showShort(getString(R.string.not_get_code));
                    return;
                }
                String newPhoneNum = mViewBinding.etPhone.getText().toString();
                if (newPhoneNum.length() != 11) {
                    ToastUtils.showShort(getString(R.string.please_input_correct_phone_number));
                    return;
                }
                String getCodePhone = (String) mViewBinding.btnVerificationCode.getTag();
                if (!newPhoneNum.equals(getCodePhone)) {//当前手机号与获取验证码的手机号不一致
                    ToastUtils.showShort(getString(R.string.please_input_correct_phone_number));
                    return;
                }
                mViewBinding.btnBinding.setEnabled(false);
                mViewBinding.btnBinding.setText(getString(R.string.binding_in));
                presenter.changePhoneNumber(StringUtils.getString(mViewBinding.etInputVerificAtionCode), newPhoneNum, this.phoneCode);
                break;
        }
    }
}
