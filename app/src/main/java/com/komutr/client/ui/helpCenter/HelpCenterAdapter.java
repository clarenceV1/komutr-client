package com.komutr.client.ui.helpCenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.example.clarence.utillibrary.CommonUtils;
import com.komutr.client.R;
import com.komutr.client.been.HelpCenter;
import com.komutr.client.common.RouterManager;


/**
 * 帮助中心列表适配器
 *
 * @version 1.0.0.0 2018/9/13 chengaobin
 */
public class HelpCenterAdapter extends BasePtrAdapter<HelpCenter, HelpCenterAdapter.ViewHolder> implements BaseViewHold.OnRecyclerViewItemClickListener {


    Context context;


    public HelpCenterAdapter(Context context) {

        this.context = context;
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.adapter_help_center);
        ViewHolder viewHolder = new ViewHolder(itemView, this);
        return viewHolder;

    }

    @Override
    public void onItemClick(View v, int position) {

        HelpCenter helpCenter = getData(position);
        if(helpCenter != null){
            RouterManager.goFAQ(helpCenter.getId());
        }

    }

    @Override
    public void onItemLongClick(View v, int HelpCenter) {

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, HelpCenter data, int position) {

        holder.ivHelpCenterName.setText(data.getTitle());
    }


    class ViewHolder extends BasePtrViewHold {

        TextView ivHelpCenterName;
        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            ivHelpCenterName = itemView.findViewById(R.id.tvHelpCenterName);
            CommonUtils.setBackground(itemView, CommonUtils.selectorStateColor(context, R.color.white, R.color.color_f1f1f4));
        }
    }

}
