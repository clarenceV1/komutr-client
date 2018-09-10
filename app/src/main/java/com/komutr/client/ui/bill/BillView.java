package com.komutr.client.ui.bill;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.Bill;
import com.komutr.client.been.RespondDO;

import java.util.List;

public interface BillView extends GodBaseView {


    void billListCallback(RespondDO<List<Bill>> respondDO);
}
