package com.komutr.client.ui.position;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.Position;
import com.komutr.client.been.RespondDO;
import com.komutr.client.ui.FrequentlyStation;

import java.util.List;

public interface PositionView extends GodBaseView {

    void searchPositionCallback(RespondDO<List<Position>> respondDO);

    void frequentlyStationCallback(RespondDO<List<FrequentlyStation>> respondDO);
}
