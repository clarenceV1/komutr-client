package com.komutr.client.ui.helpCenter;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.baseview.LoadingView;
import com.cai.pullrefresh.BaseListPtrFrameLayout;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.pullrefresh.RecycleViewDivider;
import com.cai.pullrefresh.lib.PtrFrameLayout;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.Faq;
import com.komutr.client.been.HelpCenter;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.HelpCenterBinding;
import com.komutr.client.ui.faq.FAQAdapter;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_HELP_CENTER, name = "帮助中心")
public class HelpCenterActivity extends AppBaseActivity<HelpCenterBinding> implements HelpCenterView, View.OnClickListener, BaseListPtrFrameLayout.OnPullLoadListener, LoadingView.LoadViewClickListener {

    @Inject
    HelpCenterPresenter presenter;

    int start = 0;


    int size = 10;

    HelpCenterAdapter adapter;

    PtrRecyclerView mPtrRecyclerView;


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
        setBarTitle(getString(R.string.help_center_title));
        if (titleBarView != null) {
            titleBarView.setRightText(getString(R.string.feed_back));
            titleBarView.setRightClickListener(this);
        }
        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.ptyRecycle.getRecyclerView();
        mPtrRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, DimensUtils.dp2px(this, 1f), StreamUtils.getInstance().resourceToColor(R.color.color_side_line, this)));
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new HelpCenterAdapter(this);
        mPtrRecyclerView.setAdapter(adapter);
        mViewBinding.ptyRecycle.setCloseLoadMore(true);

        mViewBinding.ptyRecycle.setOnPullLoadListener(this);
        mViewBinding.loadView.setClickListener(this);
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
        presenter.requestHelpList();
    }

    @Override
    public int getLayoutId() {
        return R.layout.help_center;
    }

    @Override
    public void helpListCallback(RespondDO<List<HelpCenter>> respondDO) {

        if (respondDO.isStatus()) {
            List<HelpCenter>  data = respondDO.getObject();
            if (data != null && data.size() > 0) {//有数据
                if (start == 0) {
                    adapter.setDatas(data);//下拉
                } else {
                    adapter.addDatas(data);//上啦
                }
                mViewBinding.ptyRecycle.setCloseLoadMore(false);
                mViewBinding.ptyRecycle.refreshOrLoadMoreComplete(true);
            } else {
                mViewBinding.ptyRecycle.refreshOrLoadMoreComplete(false);
            }
        } else {
            mViewBinding.ptyRecycle.refreshOrLoadMoreComplete(false);
        }

        if (adapter.getDatas().isEmpty()) {
            mViewBinding.loadView.setStatus(LoadingView.STATUS_NODATA);
        } else {
            mViewBinding.loadView.setStatus(LoadingView.STATUS_HIDDEN);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case com.cai.framework.R.id.tvRight://意见反馈
                RouterManager.goFeedback();
                break;
        }
    }


    @Override
    public void onRefresh(PtrFrameLayout frame) {
        start = 0;
        presenter.requestHelpList();
    }

    @Override
    public void onLoadMore() {
//        if (adapter.getCount() > size) {
//            start = adapter.getCount();
//        }
        presenter.requestHelpList();
    }

    @Override
    public void onLoadViewClick(int status) {
        start = 0;
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
        presenter.requestHelpList();
    }
}
