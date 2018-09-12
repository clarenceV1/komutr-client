package com.komutr.client.ui.main.fragment.trips;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.MyTrips;
import com.komutr.client.been.OrderDetail;
import com.komutr.client.been.RespondDO;

import java.util.List;

public interface MyTripsView extends GodBaseView {

    void orderListCallback(RespondDO<List<MyTrips>> dataList);

}
