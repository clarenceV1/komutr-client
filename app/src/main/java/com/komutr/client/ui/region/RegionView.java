package com.komutr.client.ui.region;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.Region;
import com.komutr.client.been.RegionNext;
import com.komutr.client.been.RespondDO;

import java.util.List;

public interface RegionView extends GodBaseView {


    void bigAreaCallback(RespondDO<List<Region>> respondDO);

    void nextAreaCallback(RespondDO<List<RegionNext>> respondDO);
}
