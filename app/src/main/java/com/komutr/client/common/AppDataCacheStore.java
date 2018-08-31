package com.komutr.client.common;

import com.cai.framework.dagger.ActivityScope;
import com.cai.framework.dataStore.ISharePreference;

import javax.inject.Inject;

/**
 * Created by clarence on 2018/3/26.
 */
@ActivityScope
public class AppDataCacheStore {
    @Inject
    ISharePreference sharePreference;

    @Inject
    public AppDataCacheStore() {

    }

}
