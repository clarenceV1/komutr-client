package com.komutr.client.ui.phoneNumber;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;

public interface PhoneNumberView extends GodBaseView {


    void verificationCodeCallback(RespondDO<PhoneCode> respondDO);
}
