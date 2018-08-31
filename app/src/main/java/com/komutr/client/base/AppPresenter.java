package com.komutr.client.base;

import com.komutr.client.common.AppDataStore;
import com.komutr.client.common.AppFileStore;
import com.komutr.client.common.AppRequestStore;

import javax.inject.Inject;

import dagger.Lazy;

public class AppPresenter {
    @Inject
    public Lazy<AppDataStore> dataStore;
    @Inject
    public Lazy<AppRequestStore> requestStore;
    @Inject
    public Lazy<AppFileStore> fileStore;

    @Inject
    public AppPresenter() {

    }

}
