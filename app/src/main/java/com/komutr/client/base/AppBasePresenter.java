package com.komutr.client.base;

import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.common.AppDataCacheStore;
import com.komutr.client.common.AppDataStore;
import com.komutr.client.common.AppFileStore;
import com.komutr.client.common.AppRequestStore;

import javax.inject.Inject;

import dagger.Lazy;

public abstract class AppBasePresenter<V> extends GodBasePresenter<V> {

    @Inject
    public Lazy<AppDataStore> dataStore;
    @Inject
    public Lazy<AppRequestStore> requestStore;
    @Inject
    public Lazy<AppFileStore> fileStore;
    @Inject
    public Lazy<AppDataCacheStore> cacheStore;

}
