package com.komutr.client.ui.email;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.PhoneCode;
import com.komutr.client.been.RespondDO;

public interface BindEmailView extends GodBaseView {


    void checkEmail(RespondDO<PhoneCode> respondDO);

    void bindEmailCallback(RespondDO respondDO);
}
