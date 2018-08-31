package com.komutr.client.common;


import com.cai.framework.dagger.ActivityScope;

import javax.inject.Inject;

import dagger.Lazy;
import retrofit2.Retrofit;


/**
 * Created by clarence on 2018/3/26.
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
