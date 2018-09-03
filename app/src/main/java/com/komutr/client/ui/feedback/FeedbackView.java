package com.komutr.client.ui.feedback;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.RespondDO;

public interface FeedbackView extends GodBaseView {


    void callback(RespondDO respondDO);
}
