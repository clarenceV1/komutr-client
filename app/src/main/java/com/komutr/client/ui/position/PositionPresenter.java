package com.komutr.client.ui.position;

import com.komutr.client.base.AppBasePresenter;
import com.komutr.client.been.Position;
import com.komutr.client.ui.wallet.WalletView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class PositionPresenter extends AppBasePresenter<PositionView> {

    @Inject
    public PositionPresenter() {

    }

    @Override
    public void onAttached() {
    }

    public List<Position> getTestList() {

        List<Position> list = new ArrayList<>();
        list.add(new Position());
        list.add(new Position());
        list.add(new Position());
        list.add(new Position());
        list.add(new Position());
        list.add(new Position());
        return list;
    }

    public void requestMore() {

    }

    public void requestList() {
    }
}
