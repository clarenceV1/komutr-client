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
import com.komutr.client.event.PositionEvent;
import com.komutr.client.ui.bill.BillPresenter;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * 位置列表适配器
 *
 * @version 1.0.0.0 2018/9/7 chengaobin
 */
public class PositionAdapter extends BasePtrAdapter<Position, PositionAdapter.ViewHolder> implements BaseViewHold.OnRecyclerViewItemClickListener, View.OnClickListener {


    PositionPresenter presenter;

    Context context;


    String searchText;

    boolean isStartPosition;

    public void setDatas(List<Position> datas, String searchText) {
        super.setDatas(datas);
        this.searchText = searchText;
    }

    public PositionAdapter(Context context, boolean isStartPosition,PositionPresenter presenter) {
        this.presenter = presenter;
        this.context = context;
        this.isStartPosition = isStartPosition;
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
       Position positionInfo =  getData(position);
       if(positionInfo != null){
           positionInfo.setStartPosition(isStartPosition);
           EventBus.getDefault().post(new PositionEvent(PositionEvent.CHOOSE_POSITION_SUCCESS,positionInfo));
       }

    }

    @Override
    public void onItemLongClick(View v, int position) {

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, Position data, int position) {

        holder.tvPositionName.setText(getStringBuilder(data.getName()));
        holder.ivDeleteRecord.setVisibility(data.isFrequentlyStation() ? View.VISIBLE : View.GONE);
        holder.ivDeleteRecord.setTag(data.getId());
        holder.ivDeleteRecord.setTag(R.id.tag_first, position);
        holder.ivDeleteRecord.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        if (presenter != null)
            presenter.deleteFrequentlyStation((Integer) view.getTag());
        int position = (int) view.getTag(R.id.tag_first);
        removeData(position);
    }

    class ViewHolder extends BasePtrViewHold {

        TextView tvPositionName;

        ImageView ivDeleteRecord;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            ivDeleteRecord = itemView.findViewById(R.id.ivDeleteRecord);
            tvPositionName = itemView.findViewById(R.id.tvPositionName);
            CommonUtils.setBackground(itemView, CommonUtils.selectorStateColor(context, R.color.white, R.color.color_f1f1f4));
        }
    }

}
