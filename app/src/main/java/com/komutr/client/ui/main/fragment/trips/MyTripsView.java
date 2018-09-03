package com.komutr.client.ui.main.fragment.trips;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.MyTrips;

import java.util.List;

public interface MyTripsView extends GodBaseView {

    void callback(List<MyTrips> dataList);
}
