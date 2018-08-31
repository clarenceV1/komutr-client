package com.komutr.client.common;

import com.cai.framework.dagger.ActivityScope;
import com.cai.framework.dataStore.ISharePreference;

import javax.inject.Inject;

/**
 * SharePreference应用数据存储
 */
@ActivityScope
public class AppDataStore {
    @Inject
    public ISharePreference sharePreference;

    private static final String OLD_VERSION = "old_version";

    @Inject
    public AppDataStore() {

    }

    public void saveOldVersion() {
        sharePreference.write(OLD_VERSION, false);
    }

    public boolean isOldVersion() {
        return sharePreference.read(OLD_VERSION, true);
    }

}
