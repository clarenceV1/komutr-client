package com.komutr.client.ui.orderDetails;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.BuySellTicketDetails;
import com.komutr.client.been.OrderDetail;
import com.komutr.client.been.RespondDO;

public interface OrderDetailsView extends GodBaseView {


    void orderDetailCallback(RespondDO<OrderDetail> respondDO);

    void importantCallBack(RespondDO<BuySellTicketDetails> respondDO);
}
