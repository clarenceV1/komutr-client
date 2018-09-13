package com.komutr.client.ui.faq;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
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
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.MessageDetailBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_FAQ, name = "充值常见问题")
public class FAQActivity extends AppBaseActivity<MessageDetailBinding> implements FAQView, BaseListPtrFrameLayout.OnPullLoadListener, LoadingView.LoadViewClickListener {

    @Inject
    FAQPresenter presenter;

    //内容类型 获取faq列表中的项目id 1 购票常见问题2 充值常见问题3 提现常见问题6 二维码常见问题8 栏目测试
    @Autowired(name = "contentType")
    int contentType;

    int start = 0;


    int size = 10;

    FAQAdapter adapter;

    PtrRecyclerView mPtrRecyclerView;

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
        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.ptyRecycle.getRecyclerView();
        mPtrRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, DimensUtils.dp2px(this, 1f), StreamUtils.getInstance().resourceToColor(R.color.color_side_line, this)));
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FAQAdapter(this);
        mPtrRecyclerView.setAdapter(adapter);
        mViewBinding.ptyRecycle.setCloseLoadMore(true);

        mViewBinding.ptyRecycle.setOnPullLoadListener(this);
        mViewBinding.loadView.setClickListener(this);
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
        presenter.requestDetail(contentType,start, size);
    }

    @Override
    public int getLayoutId() {
        return R.layout.message_detail;
    }

    @Override
    public void callback(RespondDO<List<Faq>> respondDO) {
        if (respondDO.isStatus()) {
            List<Faq> data = respondDO.getObject();
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
        presenter.requestDetail(contentType,start, size);
    }

    @Override
    public void onLoadMore() {
        if (adapter.getCount() > size) {
            start = adapter.getCount();
        }
        presenter.requestDetail(contentType,start, size);
    }

    @Override
    public void onLoadViewClick(int status) {
        start = 0;
        mViewBinding.loadView.setStatus(LoadingView.STATUS_LOADING);
        presenter.requestDetail(contentType,start, size);
    }
}
