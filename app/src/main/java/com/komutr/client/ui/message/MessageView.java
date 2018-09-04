package com.komutr.client.ui.message;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.RespondDO;

public interface MessageView extends GodBaseView {


    void callback(RespondDO respondDO);
}
