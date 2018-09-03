package com.komutr.client.ui.main.fragment.trips;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.cai.framework.imageload.ILoadImage;
import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.komutr.client.R;
import com.komutr.client.been.MyTrips;

public class MyTripsAdapter extends BasePtrAdapter<MyTrips,MyTripsAdapter.ViewHolder> implements BaseViewHold.OnRecyclerViewItemClickListener {
    ILoadImage iLoadImage;
    MyTripsPresenter presenter;
    Context context;

    public MyTripsAdapter(Context context, ILoadImage iLoadImage, MyTripsPresenter presenter) {
        this.iLoadImage = iLoadImage;
        this.presenter = presenter;
        this.context = context;
    }

    @Override
    protected BasePtrViewHold onPtrCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflateItemView(parent, R.layout.adapter_my_trips);

        ViewHolder viewHolder = new ViewHolder(itemView,this);
        return viewHolder;
    }

    @Override
    public void onItemClick(View v, int position) {

    }

    @Override
    public void onItemLongClick(View v, int position) {

    }

    @Override
    protected void onPtrBindViewHolder(ViewHolder holder,  MyTrips data, int position) {

//        ILoadImageParams imageParams = new ImageForGlideParams.Builder()
//                .url(data.getToken_icon())
//                .transformation(new GlideCircleTransform(context))
//                .build();
//        imageParams.setImageView(holder.imageView);
//        iLoadImage.loadImage(context, imageParams);

    }

    class ViewHolder extends BasePtrViewHold {


        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
        }
    }

}
