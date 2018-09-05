package com.komutr.client.ui.main;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Service;

public interface MainView extends GodBaseView {


    void logout(RespondDO respondDO);

    void serviceCallBack(RespondDO<Service> respondDO);
}
