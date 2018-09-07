package com.komutr.client.ui.routes;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.baseview.LoadingView;
import com.cai.framework.imageload.ILoadImage;
import com.cai.pullrefresh.BaseListPtrFrameLayout;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.pullrefresh.RecycleViewDivider;
import com.cai.pullrefresh.lib.PtrFrameLayout;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.SearchRoutesBinding;
import com.komutr.client.ui.main.fragment.trips.MyTripsAdapter;

import java.util.List;

import javax.inject.Inject;


@Route(path = RouterManager.SEARCH_ROUTES, name = "搜索-/路线搜索")
public class SearchRoutesActivity extends AppBaseActivity<SearchRoutesBinding> implements SearchRoutesView, BaseListPtrFrameLayout.OnPullLoadListener, LoadingView.LoadViewClickListener {

    @Inject
    SearchRoutesPresenter presenter;
    @Inject
    ILoadImage iLoadImage;
    @Autowired(name = "begStationId")
    int begStationId;//起点站
    @Autowired(name = "endStationId")
    int endStationId;//终点站
    PtrRecyclerView mPtrRecyclerView;

    SearchRoutesAdapter adapter;
    int offset;//偏移量
    int limit;//行数

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

        setBarTitle(getString(R.string.search_routes));

        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.ptyRecycle.getRecyclerView();
        mPtrRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, DimensUtils.dp2px(this, 10f), StreamUtils.getInstance().resourceToColor(R.color.transparent, this)));
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SearchRoutesAdapter(this, iLoadImage, presenter);
        mPtrRecyclerView.setAdapter(adapter);
        adapter.setDatas(presenter.getTestList());
        mViewBinding.ptyRecycle.setCloseLoadMore(true);

        mViewBinding.ptyRecycle.setOnPullLoadListener(this);
        mViewBinding.loadView.setClickListener(this);
        mViewBinding.loadView.setStatus(LoadingView.STATUS_HIDDEN);

        presenter.searchRoutes(begStationId,endStationId,offset,limit);
    }

    @Override
    public int getLayoutId() {
        return R.layout.search_routes;
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
    public void searchRoutes(RespondDO respondDO) {

    }
}
