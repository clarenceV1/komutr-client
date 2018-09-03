package com.komutr.client.ui.main.fragment.trips;

import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.MyTrips;
import com.komutr.client.ui.main.fragment.empty.EmptyView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MyTripsPresenter extends AppBasePresenter<MyTripsView> {

    @Inject
    public MyTripsPresenter() {

    }

    @Override
    public void onAttached() {

    }


    public void requestMore() {
        
    }

    public void requestList() {
    }

    public List<MyTrips> getTestList(){
        List<MyTrips> newdatas = new ArrayList<>();
        newdatas.add(new MyTrips());
        newdatas.add(new MyTrips());
        newdatas.add(new MyTrips());
        newdatas.add(new MyTrips());
        newdatas.add(new MyTrips());
        newdatas.add(new MyTrips());
        newdatas.add(new MyTrips());
        newdatas.add(new MyTrips());
        newdatas.add(new MyTrips());
        newdatas.add(new MyTrips());

        return newdatas;
    }
}
