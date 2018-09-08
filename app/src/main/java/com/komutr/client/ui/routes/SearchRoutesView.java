package com.komutr.client.ui.routes;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Routes;
import com.komutr.client.been.RoutesShift;
import com.komutr.client.been.SearchRoutes;

import java.util.List;

public interface SearchRoutesView extends GodBaseView {


    void searchRoutes(RespondDO<List<SearchRoutes>> respondDO);
}
