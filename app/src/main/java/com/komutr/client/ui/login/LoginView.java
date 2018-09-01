package com.komutr.client.ui.login;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.UserInfo;

public interface LoginView extends GodBaseView {

    void verificationCodeCallback(RespondDO respondDO);

    void registeredOrLoginCallBack(RespondDO respondDO);

//    void showToastMsg(String msg);
}
