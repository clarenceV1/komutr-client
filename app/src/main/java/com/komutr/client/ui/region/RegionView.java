package com.komutr.client.ui.region;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.RespondDO;

public interface RegionView extends GodBaseView {


    void bigAreaCallback(RespondDO respondDO);

    void nextAreaCallback(RespondDO respondDO);
}
