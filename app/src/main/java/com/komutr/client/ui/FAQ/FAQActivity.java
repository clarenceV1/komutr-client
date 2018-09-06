package com.komutr.client.ui.FAQ;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
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

    //内容类型 获取faq列表中的项目id 1 购票常见问题2 充值常见问题3 提现常见问题6 二维码常见问题8 栏目测试
    @Autowired(name = "contentType")
    int contentType;



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
