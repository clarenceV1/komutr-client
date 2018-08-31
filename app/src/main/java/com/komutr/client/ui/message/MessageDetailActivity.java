package com.komutr.client.ui.message;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.Constant;
import com.komutr.client.databinding.MessageDetailBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = Constant.ROUTER_MESSAGE_DETAIL, name = "消息详情")
public class MessageDetailActivity extends AppBaseActivity<MessageDetailBinding> implements MessageDetailView {

    @Inject
    MessageDetailPresenter presenter;

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
        return R.layout.message_detail;
    }
}
