package com.komutr.client.ui.routes;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cai.framework.imageload.GlideCircleTransform;
import com.cai.framework.imageload.ILoadImage;
import com.cai.framework.imageload.ILoadImageParams;
import com.cai.framework.imageload.ImageForGlideParams;
import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.komutr.client.R;
import com.komutr.client.been.Chauffeur;
import com.komutr.client.been.RouterStation;
import com.komutr.client.been.RoutesShift;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.common.Constant;
import com.komutr.client.common.RouterManager;

/**
 * 搜索路线适配器
 *
 * @version 1.0.0.0 2018/9/4 chengaobin
 */
public class SearchRoutesAdapter extends BasePtrAdapter<SearchRoutes, SearchRoutesAdapter.ViewHolder> implements BaseViewHold.OnRecyclerViewItemClickListener {
    ILoadImage iLoadImage;
    SearchRoutesPresenter presenter;
    Context context;

    public SearchRoutesAdapter(Context context, ILoadImage iLoadImage, SearchRoutesPresenter presenter) {
        this.iLoadImage = iLoadImage;
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.adapter_search_routes);

        ViewHolder viewHolder = new ViewHolder(itemView, this);
        return viewHolder;

    }

    @Override
    public void onItemClick(View v, int position) {
        RouterManager.goRouteDetail();
    }

    @Override
    public void onItemLongClick(View v, int position) {

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, final SearchRoutes data, int position) {
        RoutesShift routesShift = data.getRoutesShift();//班次绝对不会空 放心
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.goReviewPurchase(data);
            }
        });
        holder.tvBusPrice.setText(data.getTicket().getPrice());
        holder.tvBusNumber.setText(data.getRoute_id());
        String time = routesShift.getBeg_time_int();
        if (!TextUtils.isEmpty(time) && time.length() == 4) {
            boolean isAm = false;
            if (Integer.valueOf(time.substring(0, 2)) < 12) {
                isAm = true;
            }
            time = time.substring(0, 2) + ":" + time.substring(2, 4);
            holder.tvBusStartTime.setText(time + (isAm ? "am" : "pm"));
        }

        holder.tvBusStatus.setText(routesShift.getInterval() + "min");
        RouterStation routerStation = data.getStation();
        if (routerStation != null) {
            if (routerStation.getBeg() != null) {
                holder.tvStartLocation.setText(routerStation.getBeg().getName());
            }
            if (routerStation.getEnd() != null) {
                holder.tvEndLocation.setText(routerStation.getEnd().getName());
            }
        }

        Chauffeur chauffeur = routesShift.getChauffeur();
        if (chauffeur != null) {
            if (!TextUtils.isEmpty(chauffeur.getAvatar())) {
                String icon = Constant.OFFICIAL_BASE_URL.substring(0, Constant.OFFICIAL_BASE_URL.length() - 1) + chauffeur.getAvatar_thum();
                ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                        .url(icon)
                        .error(R.drawable.default_avatar)
                        .placeholder(R.drawable.default_avatar)
                        .transformation(new GlideCircleTransform(context))
                        .build();
                imageParams.setImageView(holder.ivDriverAvatar);
                iLoadImage.loadImage(context, imageParams);
            }
            holder.tvDriverPhoneNum.setText(chauffeur.getLicence_plate());
        }
    }

    class ViewHolder extends BasePtrViewHold {

        Button btnBuy;
        ImageView ivDriverAvatar;
        TextView tvDriverPhoneNum, tvBusPrice, tvBusNumber, tvStartLocation, tvEndLocation, tvBusStartTime, tvBusStatus;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            btnBuy = itemView.findViewById(R.id.btnBuy);
            ivDriverAvatar = itemView.findViewById(R.id.ivDriverAvatar);
            tvDriverPhoneNum = itemView.findViewById(R.id.tvDriverPhoneNum);
            tvBusPrice = itemView.findViewById(R.id.tvBusPrice);
            tvBusNumber = itemView.findViewById(R.id.tvBusNumber);
            tvStartLocation = itemView.findViewById(R.id.tvStartLocation);
            tvEndLocation = itemView.findViewById(R.id.tvEndLocation);
            tvBusStartTime = itemView.findViewById(R.id.tvBusStartTime);
            tvBusStatus = itemView.findViewById(R.id.tvBusStatus);
        }
    }

}
