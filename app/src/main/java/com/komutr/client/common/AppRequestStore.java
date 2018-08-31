package com.komutr.client.common;


import com.cai.framework.dagger.ActivityScope;

import javax.inject.Inject;

import dagger.Lazy;
import retrofit2.Retrofit;


/**
 * 接口Rxjava生成
 */
@ActivityScope
public class AppRequestStore {
    @Inject
    Lazy<Retrofit> retrofit;
    @Inject
    Lazy<AppDataStore> dataStore;

    @Inject
    public AppRequestStore() {
    }

}
