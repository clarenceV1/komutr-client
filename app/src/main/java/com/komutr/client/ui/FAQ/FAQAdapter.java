package com.komutr.client.ui.FAQ;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.example.clarence.utillibrary.CommonUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.komutr.client.R;
import com.komutr.client.been.Faq;

import java.util.List;


/**
 * faq列表适配器
 *
 * @version 1.0.0.0 2018/9/12 chengaobin
 */
public class FAQAdapter extends BasePtrAdapter<Faq, FAQAdapter.ViewHolder> implements BaseViewHold.OnRecyclerViewItemClickListener, View.OnClickListener {



    Context context;


    String searchText;

    public void setDatas(List<Faq> datas, String searchText) {
        super.setDatas(datas);
        this.searchText = searchText;
    }

    public FAQAdapter(Context context) {

        this.context = context;
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.adapter_faq);
        ViewHolder viewHolder = new ViewHolder(itemView, this);
        return viewHolder;

    }

    @Override
    public void onItemClick(View v, int Faq) {
//      RouterManager.goBillDetail();
    }

    @Override
    public void onItemLongClick(View v, int Faq) {

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, Faq data, int position) {

        holder.ivFaqName.setText(data.getTitle());
        holder.tvFaqContent.setText(data.getContent());
        holder.ivFaqName.setTag(position);
        holder.ivFaqName.setTag(R.id.tag_first, holder.tvFaqContent);
        holder.ivFaqName.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        TextView tvFaqContent = (TextView) view.getTag(R.id.tag_first);
        if (tvFaqContent.getVisibility() == View.GONE) {
            tvFaqContent.setVisibility(View.VISIBLE);
            ((TextView) view).setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_main,context));
        } else {
            tvFaqContent.setVisibility(View.GONE);
            ((TextView) view).setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_000000,context));
        }
        getData(position).setShow(tvFaqContent.getVisibility() == View.VISIBLE);

    }


    class ViewHolder extends BasePtrViewHold {

        TextView ivFaqName;

        TextView tvFaqContent;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            ivFaqName = itemView.findViewById(R.id.tvFaqName);
            tvFaqContent = itemView.findViewById(R.id.tvFaqContent);
            CommonUtils.setBackground(ivFaqName, CommonUtils.selectorStateColor(context, R.color.white, R.color.color_f1f1f4));
        }
    }

}
