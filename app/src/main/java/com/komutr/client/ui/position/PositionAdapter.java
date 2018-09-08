package com.komutr.client.ui.position;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.example.clarence.utillibrary.CommonUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.R;
import com.komutr.client.been.Bill;
import com.komutr.client.been.Position;
import com.komutr.client.common.RouterManager;
import com.komutr.client.ui.bill.BillPresenter;

import java.util.List;


/**
 * 位置列表适配器
 *
 * @version 1.0.0.0 2018/9/7 chengaobin
 */
public class PositionAdapter extends BasePtrAdapter<Position, PositionAdapter.ViewHolder> implements BaseViewHold.OnRecyclerViewItemClickListener {


    PositionPresenter presenter;

    Context context;


    String searchText;

    public void setDatas(List<Position> datas, String searchText) {
        super.setDatas(datas);
        this.searchText = searchText;
    }

    public PositionAdapter(Context context, PositionPresenter presenter) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.adapter_position);
        ViewHolder viewHolder = new ViewHolder(itemView, this);
        return viewHolder;

    }

    @Override
    public void onItemClick(View v, int position) {
//        RouterManager.goBillDetail();
    }

    @Override
    public void onItemLongClick(View v, int position) {

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, Position data, int position) {


    }


    private SpannableStringBuilder getStringBuilder(String text) {

        SpannableStringBuilder style = new SpannableStringBuilder(text);
        if (!StringUtils.isEmpty(searchText) && text.contains(searchText)) {
            int mainColor = StreamUtils.getInstance().resourceToColor(R.color.color_main, context);
            int otherColor = StreamUtils.getInstance().resourceToColor(R.color.color_333333, context);

            ForegroundColorSpan mainSpan = new ForegroundColorSpan(mainColor);
            ForegroundColorSpan otherSpan = new ForegroundColorSpan(otherColor);

            int index = searchText.indexOf(text);
            int length = searchText.length();
            int textLength = text.length();
            if (index == 0) {
                style.setSpan(mainSpan, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(otherSpan, length, textLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (index + 1 == length) {
                style.setSpan(otherSpan, 0, textLength - length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(mainSpan, textLength - length, textLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                style.setSpan(otherSpan, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(mainSpan, index, index + length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(otherSpan, index + length, textLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return style;
    }

    class ViewHolder extends BasePtrViewHold {

        TextView ivPositionName;

        ImageView ivDeleteRecord;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            ivDeleteRecord = itemView.findViewById(R.id.ivDeleteRecord);
            ivPositionName = itemView.findViewById(R.id.tvPositionName);
            CommonUtils.setBackground(itemView, CommonUtils.selectorStateColor(context, R.color.white, R.color.color_f1f1f4));
        }
    }

}
