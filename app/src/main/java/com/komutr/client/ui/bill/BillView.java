package com.komutr.client.ui.bill;

import com.cai.framework.base.GodBaseView;
import com.komutr.client.been.Bill;

import java.util.List;

public interface BillView extends GodBaseView {

    void callback(List<Bill> dataList);
}
