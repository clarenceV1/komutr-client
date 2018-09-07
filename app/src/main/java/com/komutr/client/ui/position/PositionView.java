package com.komutr.client.ui.position;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.Position;

import java.util.List;

public interface PositionView extends GodBaseView {

    void callback(List<Position> dataList);
}
