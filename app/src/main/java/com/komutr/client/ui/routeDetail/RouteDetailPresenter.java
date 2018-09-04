package com.komutr.client.ui.routeDetail;

import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.ui.routes.SearchRoutesView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RouteDetailPresenter extends AppBasePresenter<RouteDetailView> {

    @Inject
    public RouteDetailPresenter() {

    }

    @Override
    public void onAttached() {
    }


    
    public void requestMore() {

    }

    public void requestList() {
    }

    public List<SearchRoutes> getTestList(){
        List<SearchRoutes> newdatas = new ArrayList<>();
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());
        newdatas.add(new SearchRoutes());

        return newdatas;
    }
}
