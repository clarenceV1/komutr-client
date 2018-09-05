package com.komutr.client.ui.message;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.Message;
import com.komutr.client.been.RespondDO;

import java.util.List;

public interface MessageView extends GodBaseView {


    void callback(RespondDO<List<Message>> respondDO);
}
