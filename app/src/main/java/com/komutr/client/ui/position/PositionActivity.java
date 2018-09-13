package com.komutr.client.ui.position;

import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.Position;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.PositionBinding;
import com.komutr.client.ui.FrequentlyStation;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.POSITION, name = "搜索位置")
public class PositionActivity extends AppBaseActivity<PositionBinding> implements PositionView, BaseListPtrFrameLayout.OnPullLoadListener, View.OnClickListener, TextWatcher {

    @Inject
    PositionPresenter presenter;
    @Autowired(name = "isStartPosition")
    boolean isStartPosition;//起点站
    @Autowired(name = "bigArea")
    int bigArea;//大区 ID
    @Autowired(name = "province")
    int province;//大区下面的行政单位 ID

    PtrRecyclerView mPtrRecyclerView;

    PositionAdapter adapter;

    String value;//搜索的值
    int offset = 0;//开始行数 0
    int limit = 10;//条数 10 <offset 0 limit 10 > 取10条数据


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

        presenter.initData(isStartPosition, bigArea, province);

        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.ptyRecycle.getRecyclerView();
        mPtrRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, DimensUtils.dp2px(this, 1f), StreamUtils.getInstance().resourceToColor(R.color.transparent, this)));
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PositionAdapter(this,isStartPosition, presenter);
        mPtrRecyclerView.setAdapter(adapter);
        mViewBinding.ivBack.setOnClickListener(this);
        mViewBinding.ptyRecycle.setCloseRefresh(true);
        mViewBinding.ptyRecycle.setOnPullLoadListener(this);
        mViewBinding.etSearch.onChangedListener(this);
        presenter.requestFrequentlyStation();
    }

    @Override
    public int getLayoutId() {
        return R.layout.position;
    }


    @Override
    public void searchPositionCallback(RespondDO<List<Position>> respondDO) {


        if (respondDO.isStatus()) {
            List<Position> data = respondDO.getObject();
            if (data != null && data.size() > 0) {//有数据
                if (offset == 0) {
                    adapter.setDatas(data,StringUtils.getString(mViewBinding.etSearch));//下拉
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
            addPositionList(presenter.getFrequentlyStationList());
        }

    }

    @Override
    public void frequentlyStationCallback(RespondDO<List<FrequentlyStation>> respondDO) {

        if (respondDO.isStatus()) {
            addPositionList(respondDO.getObject());
        }
    }

    /**
     * 添加常用站点
     *
     * @param list
     */
    private void addPositionList(List<FrequentlyStation> list) {
        if (list != null && !list.isEmpty()) {
            mViewBinding.ptyRecycle.setCloseLoadMore(true);
            List<Position> positionList = new ArrayList<>();
            for (FrequentlyStation frequentlyStation : list) {
                Position position = new Position();
                position.setFrequentlyStation(true);
                position.setLatitude(isStartPosition ? frequentlyStation.getBeg_latitude() : frequentlyStation.getEnd_latitude());
                position.setLongitude(isStartPosition ? frequentlyStation.getBeg_longitude() : frequentlyStation.getEnd_longitude());
                position.setName(isStartPosition ? frequentlyStation.getBeg_station() : frequentlyStation.getEnd_station());
                position.setStation_id(isStartPosition ? frequentlyStation.getBeg_station_id() : frequentlyStation.getEnd_station_id());
                position.setId(frequentlyStation.getId());
                positionList.add(position);
            }
            adapter.setDatas(positionList);//下拉
        }
    }


    @Override
    public void onRefresh(PtrFrameLayout frame) {
//       presenter.requestList();
    }

    @Override
    public void onLoadMore() {
        if (adapter.getCount() > offset) {
            offset = adapter.getCount();
        }
        String text = StringUtils.getString(mViewBinding.etSearch);
        if (!StringUtils.isEmpty(text))
            presenter.requestList(text, offset, limit);
    }


    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String text = charSequence.toString();
        if (StringUtils.isEmpty(text)) {
            addPositionList(presenter.getFrequentlyStationList());
        } else {
            presenter.requestList(text, offset, limit);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
