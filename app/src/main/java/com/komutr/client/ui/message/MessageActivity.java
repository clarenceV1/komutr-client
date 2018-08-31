package com.komutr.client.ui.message;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.MessageBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_MESSAGE, name = "消息")
public class MessageActivity extends AppBaseActivity<MessageBinding> implements MessageView {

    @Inject
    MessagePresenter presenter;

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

    }

    @Override
    public int getLayoutId() {
        return R.layout.message;
    }
}
