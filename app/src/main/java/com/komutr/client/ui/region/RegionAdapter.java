package com.komutr.client.ui.region;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.example.clarence.utillibrary.CommonUtils;
import com.komutr.client.R;
import com.komutr.client.been.Region;

import java.util.List;


/**
 * 地区列表适配器
 *
 * @version 1.0.0.0 2018/9/9 chengaobin
 */
public class RegionAdapter extends BasePtrAdapter<Region, RegionAdapter.ViewHolder> implements BaseViewHold.OnRecyclerViewItemClickListener {


    RegionPresenter presenter;

    Context context;


    String searchText;

    public void setDatas(List<Region> datas, String searchText) {
        super.setDatas(datas);
        this.searchText = searchText;
    }

    public RegionAdapter(Context context, RegionPresenter presenter) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.adapter_region);
        ViewHolder viewHolder = new ViewHolder(itemView, this);
        return viewHolder;

    }

    @Override
    public void onItemClick(View v, int Region) {
//        RouterManager.goBillDetail();
    }

    @Override
    public void onItemLongClick(View v, int Region) {

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, Region data, int Region) {

        holder.ivRegionName.setText(data.getName());
    }




    class ViewHolder extends BasePtrViewHold {

        TextView ivRegionName;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            ivRegionName = itemView.findViewById(R.id.tvRegionName);
            CommonUtils.setBackground(itemView, CommonUtils.selectorStateColor(context, R.color.white, R.color.color_f1f1f4));
        }
    }

}
