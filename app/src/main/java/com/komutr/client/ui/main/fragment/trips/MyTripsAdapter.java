package com.komutr.client.ui.main.fragment.trips;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cai.framework.imageload.ILoadImage;
import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.example.clarence.utillibrary.DateUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.R;
import com.komutr.client.been.OrderDetail;
import com.komutr.client.been.OrderDetailDepartureTime;
import com.komutr.client.common.RouterManager;

public class MyTripsAdapter extends BasePtrAdapter<OrderDetail, MyTripsAdapter.ViewHolder> implements BaseViewHold.OnRecyclerViewItemClickListener {

    Context context;

    public MyTripsAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.adapter_my_trips);
        ViewHolder viewHolder = new ViewHolder(itemView, this);
        return viewHolder;
    }

    @Override
    public void onItemClick(View v, int position) {
        OrderDetail myTrips = datas.get(position);
        RouterManager.goOrderDetails(myTrips.getOrder_id(), false);
    }

    @Override
    public void onItemLongClick(View v, int position) {

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, OrderDetail data, int position) {


        OrderDetailDepartureTime detailDepartureTime = data.getDeparture_time();
        boolean used = true;
        if (detailDepartureTime != null) {
            String[] time = DateUtils.formatTime(System.currentTimeMillis()).split(":");
            int hour = Integer.valueOf(detailDepartureTime.getHour());
            int min = Integer.valueOf(detailDepartureTime.getMinute());
            if (Integer.valueOf(time[0]) > hour) {
                used = true;
            } else {
                used = Integer.valueOf(time[1]) >= min;
            }
            String[] ams = context.getString(R.string.am_pm).split(",");
            holder.tvMyTripsTime.setText(detailDepartureTime.getHour() + ":" + detailDepartureTime.getMinute() + (hour > 12 ? ams[1] : ams[0]));
            holder.tvMyTripsDate.setText(DateUtils.formatDate(System.currentTimeMillis(), "dd/MM/yyyy"));
        }
        holder.tvMyTripsStatus.setText(context.getString(used ? R.string.has_use : R.string.effective));
        holder.tvBusNumber.setText(data.getRoute_id());
        holder.tvLicensePlateNo.setText(data.getChauffeur() != null ? data.getChauffeur().getLicence_plate() : "");
        holder.tvStartLocation.setText(data.getStation() != null ? data.getStation().getBeg_station() : "");
        holder.tvEndLocation.setText(data.getStation() != null ? data.getStation().getEnd_station() : "");
        holder.tvTicketPrice.setText(context.getString(R.string.ticket_price_price,data.getAmount()));
        if (used) {//已使用
            int colorGrey = StreamUtils.getInstance().resourceToColor(R.color.color_999999, context);
            holder.tvMyTripsTime.setTextColor(colorGrey);
            holder.tvMyTripsDate.setTextColor(colorGrey);
            holder.tvMyTripsStatus.setTextColor(colorGrey);
            holder.tvBusNumber.setTextColor(colorGrey);
            holder.tvLicensePlateNo.setTextColor(colorGrey);
            holder.tvStartLocation.setTextColor(colorGrey);
            holder.tvEndLocation.setTextColor(colorGrey);
            holder.tvTicketPrice.setTextColor(colorGrey);
        } else {//未使用
            int colorBlack = StreamUtils.getInstance().resourceToColor(R.color.color_000000, context);
            holder.tvMyTripsTime.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_dc4c4c, context));
            holder.tvMyTripsDate.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_333333, context));
            holder.tvMyTripsStatus.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_24b722, context));
            holder.tvBusNumber.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_dc4c4c, context));
            holder.tvLicensePlateNo.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_666666, context));
            holder.tvStartLocation.setTextColor(colorBlack);
            holder.tvEndLocation.setTextColor(colorBlack);
            holder.tvTicketPrice.setTextColor(colorBlack);
        }


    }

    class ViewHolder extends BasePtrViewHold {

        TextView tvMyTripsTime;
        TextView tvMyTripsDate;
        TextView tvMyTripsStatus;
        TextView tvBusNumber;
        TextView tvLicensePlateNo;
        TextView tvStartLocation;
        TextView tvEndLocation;
        TextView tvTicketPrice;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            tvMyTripsTime = itemView.findViewById(R.id.tvMyTripsTime);
            tvMyTripsDate = itemView.findViewById(R.id.tvMyTripsDate);
            tvMyTripsStatus = itemView.findViewById(R.id.tvMyTripsStatus);
            tvBusNumber = itemView.findViewById(R.id.tvBusNumber);
            tvLicensePlateNo = itemView.findViewById(R.id.tvLicensePlateNo);
            tvStartLocation = itemView.findViewById(R.id.tvStartLocation);
            tvEndLocation = itemView.findViewById(R.id.tvEndLocation);
            tvTicketPrice = itemView.findViewById(R.id.tvTicketPrice);
        }
    }

}
