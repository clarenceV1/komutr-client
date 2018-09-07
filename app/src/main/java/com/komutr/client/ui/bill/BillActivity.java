package com.komutr.client.ui.bill;

import android.support.v7.widget.LinearLayoutManager;

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
import com.komutr.client.been.Bill;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.BillBinding;
import com.komutr.client.ui.routes.SearchRoutesAdapter;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.BILL, name = "我的-我的钱包-账单")
public class BillActivity extends AppBaseActivity<BillBinding> implements BillView, BaseListPtrFrameLayout.OnPullLoadListener, LoadingView.LoadViewClickListener {

    @Inject
    BillPresenter presenter;

    PtrRecyclerView mPtrRecyclerView;

    BillAdapter adapter;

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
       setBarTitle(getString(R.string.transactions));

        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.ptyRecycle.getRecyclerView();
        mPtrRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, DimensUtils.dp2px(this, 1f), StreamUtils.getInstance().resourceToColor(R.color.transparent, this)));
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BillAdapter(this, presenter);
        mPtrRecyclerView.setAdapter(adapter);
        adapter.setDatas(presenter.getTestList());
        mViewBinding.ptyRecycle.setCloseLoadMore(true);

        mViewBinding.ptyRecycle.setOnPullLoadListener(this);
        mViewBinding.loadView.setClickListener(this);
        mViewBinding.loadView.setStatus(LoadingView.STATUS_HIDDEN);
    }

    @Override
    public int getLayoutId() {

        return R.layout.bill;
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
    public void onLoadViewClick(int status) {
        presenter.requestList();
    }

    @Override
    public void callback(List<Bill> dataList) {

    }
}
