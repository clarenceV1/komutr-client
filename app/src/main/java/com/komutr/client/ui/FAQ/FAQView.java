package com.komutr.client.ui.FAQ;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.Faq;
import com.komutr.client.been.RespondDO;

import java.util.List;

public interface FAQView extends GodBaseView {


    void callback(RespondDO<List<Faq>> respondDO);
}
