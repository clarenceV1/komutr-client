package com.komutr.client.ui.position;

import android.app.Activity;
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
import com.komutr.client.been.Position;
import com.komutr.client.event.PositionEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;


/**
 * 位置列表适配器
 *
 * @version 1.0.0.0 2018/9/7 chengaobin
 */
public class PositionAdapter extends BasePtrAdapter<Position, PositionAdapter.ViewHolder> implements BaseViewHold.OnRecyclerViewItemClickListener, View.OnClickListener {


    PositionPresenter presenter;

    Activity activity;


    String searchText;

    boolean isStartPosition;

    public void setDatas(List<Position> datas, String searchText) {
        super.setDatas(datas);
        this.searchText = searchText;
    }

    public PositionAdapter(Activity activity, boolean isStartPosition, PositionPresenter presenter) {
        this.presenter = presenter;
        this.activity = activity;
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
        Position positionInfo = getData(position);
        if (positionInfo != null) {
            positionInfo.setStartPosition(isStartPosition);
            EventBus.getDefault().post(new PositionEvent(PositionEvent.CHOOSE_POSITION_SUCCESS, positionInfo));
            activity.finish();
        }


    }

    @Override
    public void onItemLongClick(View v, int position) {

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, Position data, int position) {
        holder.tvPositionName.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_333333, activity));
        holder.tvPositionName.setText(data.isFrequentlyStation() ? data.getName():getStringBuilder(data.getName()));
        holder.ivDeleteRecord.setVisibility(data.isFrequentlyStation() ? View.VISIBLE : View.GONE);
        holder.ivDeleteRecord.setTag(data.getId());
        holder.ivDeleteRecord.setTag(R.id.tag_first, position);
        holder.ivDeleteRecord.setOnClickListener(this);
    }


    private SpannableStringBuilder getStringBuilder(String text) {

        SpannableStringBuilder style = new SpannableStringBuilder(text);
        int index;
        if (!StringUtils.isEmpty(searchText) && (index = text.indexOf(searchText)) >= 0) {
            int mainColor = StreamUtils.getInstance().resourceToColor(R.color.color_main, activity);
            int otherColor = StreamUtils.getInstance().resourceToColor(R.color.color_333333, activity);

            ForegroundColorSpan mainSpan = new ForegroundColorSpan(mainColor);
            ForegroundColorSpan otherSpan = new ForegroundColorSpan(otherColor);
            int length = searchText.length();
            int textLength = text.length();
            if (index == 0) {
                style.setSpan(mainSpan, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(otherSpan, length, textLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else if (index + 1 +length == textLength) {//在最后的位置
                style.setSpan(otherSpan, 0, index, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                style.setSpan(mainSpan, index, textLength, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
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
            CommonUtils.setBackground(itemView, CommonUtils.selectorStateColor(activity, R.color.white, R.color.color_f1f1f4));
        }
    }

}
