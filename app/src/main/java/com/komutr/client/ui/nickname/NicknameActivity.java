package com.komutr.client.ui.nickname;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.NicknameBinding;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_NICKNAME, name = "昵称")
public class NicknameActivity extends AppBaseActivity<NicknameBinding> implements NicknameView {
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
        mViewBinding.tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nickName = mViewBinding.editNicke.getText().toString();
                presenter.checkUsername(nickName);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.nickname;
    }

    @Override
    public void updateMyData(RespondDO respondDO) {

    }

    @Override
    public void checkUsername(RespondDO respondDO) {

    }

}
