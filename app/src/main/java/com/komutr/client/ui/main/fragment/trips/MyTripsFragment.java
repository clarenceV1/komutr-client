package com.komutr.client.ui.main.fragment.trips;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

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
import com.komutr.client.base.AppBaseFragment;
import com.komutr.client.been.OrderDetail;
import com.komutr.client.been.RespondDO;
import com.komutr.client.databinding.FragmentMyTripsBinding;

import java.util.List;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyTripsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTripsFragment extends AppBaseFragment<FragmentMyTripsBinding> implements MyTripsView, BaseListPtrFrameLayout.OnPullLoadListener, LoadingView.LoadViewClickListener {

    @Inject
    MyTripsPresenter presenter;


    PtrRecyclerView mPtrRecyclerView;
    MyTripsAdapter adapter;
    int start = 0;
    int size = 10;

    @Inject
    ILoadImage iLoadImage;

    public MyTripsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyTripsFragment newInstance() {
        MyTripsFragment fragment = new MyTripsFragment();
        return fragment;
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List observerList) {
        observerList.add(presenter);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_my_trips;
    }

    @Override
    public void initView(View view) {
        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.ptyRecycle.getRecyclerView();
        mPtrRecyclerView.addItemDecoration(new RecycleViewDivider(activity, LinearLayoutManager.VERTICAL, DimensUtils.dp2px(activity, 10f), StreamUtils.getInstance().resourceToColor(R.color.transparent, activity)));
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new MyTripsAdapter(activity);
        mPtrRecyclerView.setAdapter(adapter);
        mViewBinding.ptyRecycle.setCloseLoadMore(true);

        mViewBinding.ptyRecycle.setOnPullLoadListener(this);
        mViewBinding.loadView.setClickListener(this);
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
        presenter.requestList(start, size);
    }

    @Override
    public void orderListCallback(RespondDO<List<OrderDetail>> respondDO) {
        if (respondDO.isStatus()) {
            List<OrderDetail> data = respondDO.getObject();
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
    public void onRefresh(PtrFrameLayout frame) {
        start = 0;
        presenter.requestList(start, size);
    }

    @Override
    public void onLoadMore() {
        if (adapter.getCount() > size) {
            start = adapter.getCount();
        }
        presenter.requestMore();
    }

    @Override
    public void onLoadViewClick(int status) {
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
        presenter.requestList(start, size);
    }
}
