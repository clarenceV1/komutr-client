package com.komutr.client.ui.position;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
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
import com.komutr.client.been.Position;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.PositionBinding;


import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.POSITION, name = "搜索位置")
public class PositionActivity extends AppBaseActivity<PositionBinding> implements PositionView , BaseListPtrFrameLayout.OnPullLoadListener, LoadingView.LoadViewClickListener,View.OnClickListener{


    @Inject
    PositionPresenter presenter;

    @Autowired(name = "isStartPosition")
    boolean isStartPosition;//起点站


    PtrRecyclerView mPtrRecyclerView;

    PositionAdapter adapter;

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


        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.ptyRecycle.getRecyclerView();
        mPtrRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, DimensUtils.dp2px(this, 1f), StreamUtils.getInstance().resourceToColor(R.color.transparent, this)));
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PositionAdapter(this, presenter);
        mPtrRecyclerView.setAdapter(adapter);
        adapter.setDatas(presenter.getTestList());
        mViewBinding.ivBack.setOnClickListener(this);
        mViewBinding.ptyRecycle.setCloseLoadMore(true);
        mViewBinding.ptyRecycle.setOnPullLoadListener(this);
        mViewBinding.loadView.setClickListener(this);
        mViewBinding.loadView.setStatus(LoadingView.STATUS_HIDDEN);

    }

    @Override
    public int getLayoutId() {
        return R.layout.position;
    }


    @Override
    public void callback(List<Position> dataList) {

    }

    @Override
    public void onLoadViewClick(int status) {
        presenter.requestList();
    }

    @Override
    public void onRefresh(PtrFrameLayout frame) {
        presenter.requestList();
    }

    @Override
    public void onLoadMore() {
        presenter.requestMore();
    }


    @Override
    public void onClick(View view) {
        finish();
    }
}
