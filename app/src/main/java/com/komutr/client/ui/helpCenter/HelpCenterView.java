package com.komutr.client.ui.helpCenter;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.HelpCenter;
import com.komutr.client.been.RespondDO;

import java.util.List;

public interface HelpCenterView extends GodBaseView {


    void helpListCallback(RespondDO<List<HelpCenter>> respondDO);

    void detailCallback(RespondDO respondDO);
}
