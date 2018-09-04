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
import com.komutr.client.been.MyTrips;
import com.komutr.client.databinding.FragmentMyTripsBinding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyTripsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTripsFragment extends AppBaseFragment<FragmentMyTripsBinding> implements MyTripsView {

    @Inject
    MyTripsPresenter presenter;
    PtrRecyclerView mPtrRecyclerView;
    MyTripsAdapter adapter;
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
        mPtrRecyclerView.addItemDecoration(new RecycleViewDivider(activity, LinearLayoutManager.VERTICAL, DimensUtils.dp2px(activity,10f), StreamUtils.getInstance().resourceToColor(R.color.transparent,activity)));
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new MyTripsAdapter(getContext(), iLoadImage, presenter);
        mPtrRecyclerView.setAdapter(adapter);
        adapter.setDatas(presenter.getTestList());
        mViewBinding.ptyRecycle.setCloseLoadMore(true);
        mViewBinding.ptyRecycle.setOnPullLoadListener(new BaseListPtrFrameLayout.OnPullLoadListener() {
            @Override
            public void onRefresh(final PtrFrameLayout frame) {
                presenter.requestList();
            }

            @Override
            public void onLoadMore() {
                presenter.requestMore();
            }
        });
        mViewBinding.loadView.setClickListener(new LoadingView.LoadViewClickListener() {

            @Override
            public void onLoadViewClick(int status) {
                presenter.requestList();
            }
        });
        mViewBinding.loadView.setStatus(LoadingView.STATUS_HIDDEN);
//      mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
    }

    @Override
    public void callback(List<MyTrips> data) {
        if (data != null && data.size() > 0) {//有数据
//            if (timestamp == 0) {
//                adapter.setDatas(data.getList());//下拉
//            } else {
//                adapter.addDatas(data.getList());//上啦
//            }
            mViewBinding.ptyRecycle.setCloseLoadMore(false);
            mViewBinding.ptyRecycle.refreshOrLoadMoreComplete(true);
        } else {
            mViewBinding.ptyRecycle.refreshOrLoadMoreComplete(false);
        }

        if (adapter.getDatas().isEmpty()) {
            mViewBinding.loadView.setStatus(LoadingView.STATUS_NODATA);
        } else {
            mViewBinding.loadView.setStatus(LoadingView.STATUS_HIDDEN);
        }
    }
}
