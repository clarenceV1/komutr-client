package com.komutr.client.ui.message.details;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.MessageDetails;
import com.komutr.client.been.RespondDO;

public interface MessageDetailsView extends GodBaseView {


    void callback(RespondDO<MessageDetails> respondDO);
}
