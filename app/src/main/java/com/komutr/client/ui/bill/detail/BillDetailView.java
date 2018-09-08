package com.komutr.client.ui.bill.detail;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.BillDetail;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Wallet;

public interface BillDetailView extends GodBaseView {


    void billDetailCallBack(RespondDO<BillDetail> respondDO);

    void deleteBillCallBack(RespondDO<String> respondDO);
}
