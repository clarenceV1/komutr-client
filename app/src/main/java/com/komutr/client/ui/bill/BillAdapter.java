package com.komutr.client.ui.bill;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.komutr.client.R;
import com.komutr.client.been.Bill;
import com.komutr.client.been.BillItem;
import com.komutr.client.common.RouterManager;


/**
 * 账单列表适配器
 *
 * @version 1.0.0.0 2018/9/4 chengaobin
 */
public class BillAdapter extends BasePtrAdapter<Bill, BillAdapter.ViewHolder> implements BaseViewHold.OnRecyclerViewItemClickListener {


    BillPresenter presenter;

    Context context;

    public BillAdapter(Context context, BillPresenter presenter) {
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.adapter_bill);
        ViewHolder viewHolder = new ViewHolder(itemView, this);
        return viewHolder;

    }

    @Override
    public void onItemClick(View v, int position) {
        Bill bill = datas.get(position);
        RouterManager.goBillDetail(bill.getId());
    }

    @Override
    public void onItemLongClick(View v, int position) {

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, Bill data, int position) {

        BillItem billItem = data.getItem();
        if (billItem != null) {
            holder.tvBillMethod.setText(billItem.getItem_name());
            holder.tvBillAmount.setText("+ ₱ " + billItem.getAmount());
        }
        holder.tvBillName.setText(data.getCreated());

    }

    class ViewHolder extends BasePtrViewHold {
        TextView tvBillMethod, tvBillName, tvBillAmount;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            tvBillMethod = itemView.findViewById(R.id.tvBillMethod);
            tvBillName = itemView.findViewById(R.id.tvBillName);
            tvBillAmount = itemView.findViewById(R.id.tvBillAmount);
        }
    }

}
