package com.komutr.client.ui.main.fragment.book;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.RoutesInfo;
import com.komutr.client.been.Station;

import java.util.List;

public interface BookView extends GodBaseView {


    void requestNearbyCallback(RespondDO<Station> respondDO);

    void routesInfoCallback(RespondDO<RoutesInfo> respondDO);
}


