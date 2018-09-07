package com.komutr.client.ui.position;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.Position;
import com.komutr.client.been.RespondDO;

import java.util.List;

public interface PositionView extends GodBaseView {

    void callback(List<Position> dataList);

    void requestListCallback(RespondDO respondDO);
}
