package com.komutr.client.ui.bill;

import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.Bill;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class BillPresenter extends AppBasePresenter<BillView> {

    @Inject
    public BillPresenter() {

    }

    @Override
    public void onAttached() {
    }


    public List<Bill> getTestList() {

        List<Bill> list = new ArrayList<>();
        list.add(new Bill());
        list.add(new Bill());
        list.add(new Bill());
        list.add(new Bill());
        list.add(new Bill());
        list.add(new Bill());
        return  list;

    }


    public void requestMore() {

    }

    public void requestList() {
    }
}
