package com.komutr.client.ui.region;

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
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.OrderDetail;
import com.komutr.client.been.Region;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.RegionBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_REGION, name = "我的-个人信息-地区")
public class RegionActivity extends AppBaseActivity<RegionBinding> implements RegionView, View.OnClickListener {
    @Inject
    RegionPresenter presenter;


    PtrRecyclerView mPtrRecyclerView;
    RegionAdapter adapter;


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
        setBarTitle(getString(R.string.region_title));

        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.ptyRecycle.getRecyclerView();
        mPtrRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, DimensUtils.dp2px(this, 1f), StreamUtils.getInstance().resourceToColor(R.color.transparent, this)));
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RegionAdapter(this, presenter);
        mPtrRecyclerView.setAdapter(adapter);
//        adapter.setDatas(presenter.getTestList());
//        mViewBinding.ptyRecycle.setCloseLoadMore(true);
//        mViewBinding.ptyRecycle.setOnPullLoadListener(this);
//        mViewBinding.loadView.setClickListener(this);
        onShowLoadDialog(getString(R.string.loading), this);
        presenter.requestBigArea();
    }

    @Override
    public int getLayoutId() {
        return R.layout.region;
    }

    @Override
    public void bigAreaCallback(RespondDO respondDO) {
        hiddenDialog();
        if (respondDO.isStatus()) {
            List<Region> data = (List<Region>) respondDO.getObject();
            if (data != null && data.size() > 0) {//有数据
                adapter.setDatas(data);//下拉
            }
        } else {
            if (adapter.getCount() > 0) {
                ToastUtils.showShort(respondDO.getMsg());
            } else {
                onShowLoadError();
            }
        }
    }

    @Override
    public void nextAreaCallback(RespondDO respondDO) {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_reloading_text:
                onShowLoadDialog(getString(R.string.loading), this);
                presenter.requestBigArea();
                break;
        }
    }
}
