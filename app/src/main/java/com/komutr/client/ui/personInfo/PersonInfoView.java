package com.komutr.client.ui.personInfo;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.RespondDO;

public interface PersonInfoView extends GodBaseView {


    void callbackUserInfo(RespondDO respondDO);

    void callbackAvatar(RespondDO respondDO);
}
