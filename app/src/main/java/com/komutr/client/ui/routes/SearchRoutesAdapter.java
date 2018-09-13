package com.komutr.client.ui.routes;

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
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.R;
import com.komutr.client.been.Chauffeur;
import com.komutr.client.been.RouteDetail;
import com.komutr.client.been.RouterStation;
import com.komutr.client.been.Routes;
import com.komutr.client.been.RoutesSetOff;
import com.komutr.client.been.RoutesTime;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.been.Ticket;
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
    SearchRoutesActivity context;

    public SearchRoutesAdapter(SearchRoutesActivity context, ILoadImage iLoadImage, SearchRoutesPresenter presenter) {
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
        SearchRoutes data = getData(position);
        if (data != null && data.getRoute() != null && data.getRoute().getTicket() != null) {
            RouteDetail routeDetail = new RouteDetail();
            routeDetail.setRouteId(data.getRoute().getRoute_id());
            routeDetail.setShiftId(data.getId());
            routeDetail.setLineNumber(data.getRoute().getLine_number());
            routeDetail.setPrice(data.getRoute().getTicket().getPrice());
            RouterManager.goRouteDetail(routeDetail);
        }
    }

    @Override
    public void onItemLongClick(View v, int position) {

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder, final SearchRoutes data, int position) {
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.goReviewPurchase(data);
            }
        });
        RoutesTime routesTime = data.getTimer();
        if (routesTime != null) {
            RoutesSetOff setOff = routesTime.getIs_setoff();
            if (setOff.isIs_setoff()) {
                holder.tvBusStatus.setText(setOff.getTime_left());
            } else {
                holder.tvBusStatus.setText(context.getString(R.string.bus_already_start));
            }
            holder.tvBusStartTime.setText(routesTime.getBeg_time() + routesTime.getInterval());
        }

        Routes route = data.getRoute();
        if (route != null) {
            if (route.getStation() != null) {
                RouterStation routerStation = route.getStation();
                if (routerStation != null) {
                    if (routerStation.getBeg() != null) {
                        holder.tvStartLocation.setText(routerStation.getBeg().getName());
                    }
                    if (routerStation.getEnd() != null) {
                        holder.tvEndLocation.setText(routerStation.getEnd().getName());
                    }
                }
            }
            holder.tvBusNumber.setText(route.getRoute_id());
            if (route.getTicket() != null) {
                Ticket ticket = route.getTicket();
                holder.tvBusPrice.setText(ticket.getPrice());
            } else {
                holder.tvBusPrice.setText(context.getString(R.string.default_amount));
            }
        }

        Chauffeur chauffeur = data.getChauffeur();
        if (chauffeur != null) {
            if (!StringUtils.isEmpty(chauffeur.getAvatar())) {
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
