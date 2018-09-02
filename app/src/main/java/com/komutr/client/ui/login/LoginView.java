package com.komutr.client.ui.login;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.RespondDO;

public interface LoginView extends GodBaseView {

    void verificationCodeCallback(RespondDO respondDO);

    void registeredOrLoginCallBack(RespondDO respondDO);

}
