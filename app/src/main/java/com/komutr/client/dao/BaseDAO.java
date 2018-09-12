package com.komutr.client.dao;

import com.komutr.client.base.App;
import com.komutr.client.been.MyObjectBox;

import io.objectbox.BoxStore;

public class BaseDAO {

    public BoxStore boxStore;

    public BaseDAO() {
        this.boxStore = App.getBoxStore();
    }
}
