package com.komutr.client.ui.routes;

import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.ui.wallet.WalletView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SearchRoutesPresenter extends AppBasePresenter<SearchRoutesView> {

    @Inject
    public SearchRoutesPresenter() {

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
