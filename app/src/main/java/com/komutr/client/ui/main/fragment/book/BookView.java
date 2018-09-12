package com.komutr.client.ui.main.fragment.book;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Station;

public interface BookView extends GodBaseView {

    void requestNearbyCallback(RespondDO<Station> respondDO);
}


