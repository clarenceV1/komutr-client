package com.komutr.client.ui.nickname;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.NicknameBinding;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_NICKNAME, name = "我的-个人信息-昵称")
public class NicknameActivity extends AppBaseActivity<NicknameBinding> implements NicknameView, View.OnClickListener, TextWatcher {
    @Inject
    NicknamePresenter presenter;

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
        mViewBinding.rbSave.setOnClickListener(this);
        mViewBinding.etNickName.addTextChangedListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.nickname;
    }

    @Override
    public void updateMyData(RespondDO respondDO) {
        hiddenDialog();
        ToastUtils.showShort(respondDO.getMsg());
        if(respondDO.isStatus()){
            finish();
        }
    }

    @Override
    public void checkUsername(RespondDO respondDO) {
     if(!respondDO.isStatus()){
         hiddenDialog();
         ToastUtils.showShort(respondDO.getMsg());
     }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbSave://保存
                showDialog(getString(R.string.updating),true);
                String nickName = mViewBinding.etNickName.getText().toString();
                presenter.checkUsername(nickName);
                break;
            case R.id.ivBack:
                finish();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int length = charSequence.toString().length();
        mViewBinding.tvContentLength.setText(length + "/20");
        mViewBinding.rbSave.setChecked(length > 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
