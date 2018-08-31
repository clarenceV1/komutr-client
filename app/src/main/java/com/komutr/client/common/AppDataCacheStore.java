package com.komutr.client.common;

import com.cai.framework.dagger.ActivityScope;
import com.cai.framework.dataStore.ISharePreference;

import javax.inject.Inject;

/**
 * SharePreference缓存数据
 */
@ActivityScope
public class AppDataCacheStore {
    @Inject
    ISharePreference sharePreference;

    @Inject
    public AppDataCacheStore() {

    }

}
