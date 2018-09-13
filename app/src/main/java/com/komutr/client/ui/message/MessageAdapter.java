package com.komutr.client.ui.message;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cai.framework.widget.dialog.GodDialog;
import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.example.clarence.utillibrary.CommonUtils;
import com.example.clarence.utillibrary.DateUtils;
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

    Activity activity;


    String searchText;

    public void setDatas(List<Message> datas, String searchText) {
        super.setDatas(datas);
        this.searchText = searchText;
    }

    public MessageAdapter(Activity activity, MessagePresenter presenter) {
        this.presenter = presenter;
        this.activity = activity;
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.adapter_message);
        ViewHolder viewHolder = new ViewHolder(itemView, this);
        return viewHolder;

    }

    @Override
    public void onItemClick(View v, int position) {
//      RouterManager.goBillDetail();
        Message msg = getData(position);
        if (msg != null && msg.getStatus() == 0) {
            presenter.updateStatus(msg.getId(), 1);
            msg.setStatus(1);
            v.findViewById(R.id.viewRedPoint).setVisibility(View.INVISIBLE);
        }
        RouterManager.goMessageDetails(msg.getContent());
    }

    /**
     * 显示删除消息对话框
     */
    private void showMsgDialog(final int position) {
        new GodDialog.Builder(activity)
                .setTitle(activity.getString(R.string.delete_msg))
                .setMessage(activity.getString(R.string.delete_msg_content))
                .setNegativeButton(activity.getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                       ToastUtils.showShort("点击取消了");
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(activity.getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                       ToastUtils.showShort("点击确定了");
                        Message msg = getData(position);
                        if (msg != null && msg.getStatus() != -1) {
                            presenter.updateStatus(msg.getId(), -1);
                            removeData(position);
                        }
                        dialog.dismiss();

                    }
                }).build().show();
    }

    @Override
    public void onItemLongClick(View v, int position) {
        showMsgDialog(position);
    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, Message data, int Message) {

        holder.tvMsgTitle.setText(data.getTitle());
        holder.tvMsgContent.setText(data.getContent());
        holder.tvMsgDate.setText(data.getCreate_at());
        holder.viewRedPoint.setVisibility(data.getStatus() == 0 ? View.VISIBLE : View.INVISIBLE);
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
            viewRedPoint = itemView.findViewById(R.id.viewRedPoint);
            CommonUtils.setBackground(itemView, CommonUtils.selectorStateColor(activity, R.color.white, R.color.color_f1f1f4));
        }
    }

}
