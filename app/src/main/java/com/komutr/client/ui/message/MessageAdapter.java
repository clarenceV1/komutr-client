package com.komutr.client.ui.message;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.example.clarence.utillibrary.CommonUtils;
import com.komutr.client.R;
import com.komutr.client.been.Message;
import com.komutr.client.common.RouterManager;

import java.util.List;


/**
 * 地区列表适配器
 *
 * @version 1.0.0.0 2018/9/9 chengaobin
 */
public class MessageAdapter extends BasePtrAdapter<Message, MessageAdapter.ViewHolder> implements BaseViewHold.OnRecyclerViewItemClickListener {


    MessagePresenter presenter;

    Context context;


    String searchText;

    public void setDatas(List<Message> datas, String searchText) {
        super.setDatas(datas);
        this.searchText = searchText;
    }

    public MessageAdapter(Context context, MessagePresenter presenter) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.adapter_message);
        ViewHolder viewHolder = new ViewHolder(itemView, this);
        return viewHolder;

    }

    @Override
    public void onItemClick(View v, int position) {
//        RouterManager.goBillDetail();
        RouterManager.goMessageDetails(getData(position).getContent());
    }

    @Override
    public void onItemLongClick(View v, int Message) {

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, Message data, int Message) {

        holder.tvMsgTitle.setText(data.getTitle());
        holder.tvMsgContent.setText(data.getContent());
        holder.tvMsgDate.setText(data.getCreate_at());
    }




    class ViewHolder extends BasePtrViewHold {

        TextView tvMsgTitle;
        TextView tvMsgContent;
        TextView tvMsgDate;
        View viewRedPoint;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            tvMsgTitle = itemView.findViewById(R.id.tvMsgTitle);
            tvMsgContent = itemView.findViewById(R.id.tvMsgContent);
            tvMsgDate = itemView.findViewById(R.id.tvMsgDate);
            viewRedPoint= itemView.findViewById(R.id.viewRedPoint);
            CommonUtils.setBackground(itemView, CommonUtils.selectorStateColor(context, R.color.white, R.color.color_f1f1f4));
        }
    }

}
