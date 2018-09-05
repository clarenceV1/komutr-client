package com.komutr.client.ui.FAQ;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.Faq;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.MessageDetailBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_FAQ, name = "充值常见问题")
public class FAQActivity extends AppBaseActivity<MessageDetailBinding> implements FAQView {

    @Inject
    FAQPresenter presenter;
    @Autowired(name = "contentType")
    int contentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }

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
        setBarTitle(getString(R.string.faq_title));
        presenter.requestDetail(contentType);
    }

    @Override
    public int getLayoutId() {
        return R.layout.message_detail;
    }

    @Override
    public void callback(RespondDO<List<Faq>> respondDO) {
        if(respondDO.isStatus()){

        }
    }
}
