package com.komutr.client.ui.routes;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cai.framework.imageload.ILoadImage;
import com.cai.pullrefresh.BasePtrAdapter;
import com.cai.pullrefresh.BasePtrViewHold;
import com.cai.pullrefresh.BaseViewHold;
import com.komutr.client.R;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.common.RouterManager;


/**
 * 搜索路线适配器
 *
 * @version 1.0.0.0 2018/9/4 chengaobin
 */
public class SearchRoutesAdapter extends BasePtrAdapter<SearchRoutes,SearchRoutesAdapter.ViewHolder> implements BaseViewHold.OnRecyclerViewItemClickListener,View.OnClickListener {
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

        ViewHolder viewHolder = new ViewHolder(itemView,this);
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
    protected void onPtrBindViewHolder(ViewHolder holder,  SearchRoutes data, int position) {

        holder.btnBuy.setOnClickListener(this);
//        ILoadImageParams imageParams = new ImageForGlideParams.Builder()
//                .url(data.getToken_icon())
//                .transformation(new GlideCircleTransform(context))
//                .build();
//        imageParams.setImageView(holder.imageView);
//        iLoadImage.loadImage(context, imageParams);

    }

    @Override
    public void onClick(View view) {
        RouterManager.goReviewPurchase();
    }

    class ViewHolder extends BasePtrViewHold {

        Button btnBuy;

        public ViewHolder(View itemView, OnRecyclerViewItemClickListener listener) {
            super(itemView, listener);
            btnBuy = itemView.findViewById(R.id.btnBuy);
        }
    }

}
