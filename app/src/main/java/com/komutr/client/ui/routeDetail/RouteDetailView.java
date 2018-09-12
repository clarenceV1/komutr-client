package com.komutr.client.ui.routeDetail;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.RoutesInfo;

/**
 * 路线详情
 */
public interface RouteDetailView extends GodBaseView {

    void routesInfoCallback(RespondDO<RoutesInfo> respondDO);
}
