package com.komutr.client.ui.feedback;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.FeedbackBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_FEEDBACK, name = "意见反馈")
public class FeedbackActivity extends AppBaseActivity<FeedbackBinding> implements FeedbackView {
    @Inject
    FeedbackPresenter presenter;

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
        setBarTitle(getString(R.string.feedback_title));
        mViewBinding.tvFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.requestFeedBack("Wo yao ceshi");
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.feedback;
    }

    @Override
    public void callback(RespondDO respondDO) {

    }
}
