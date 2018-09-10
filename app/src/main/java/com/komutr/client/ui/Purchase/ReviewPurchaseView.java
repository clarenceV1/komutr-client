package com.komutr.client.ui.Purchase;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.BuySellTicketDetails;
import com.komutr.client.been.BuyTicket;
import com.komutr.client.been.RespondDO;

public interface ReviewPurchaseView extends GodBaseView {


    void purchaseTicketCallBack(RespondDO<BuyTicket> respondDO);

    void buySellTicketDetailsCallBack(RespondDO<BuySellTicketDetails> respondDO);
}
