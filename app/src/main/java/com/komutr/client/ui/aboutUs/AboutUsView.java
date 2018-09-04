package com.komutr.client.ui.aboutUs;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.AboutUs;
import com.komutr.client.been.RespondDO;

public interface AboutUsView extends GodBaseView {


    void callback(RespondDO<AboutUs> respondDO);
}
