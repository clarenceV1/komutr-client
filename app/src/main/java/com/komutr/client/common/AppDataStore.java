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

    private static final String GE_TUI_PUSH_CLIENT_ID = "GeTuiPushClientId";

    @Inject
    public AppDataStore() {

    }

    /**
     * 个推ClientId
     *
     * @param clientid
     */
    public void setGeTuiPushClientId(String clientid) {
        sharePreference.write(GE_TUI_PUSH_CLIENT_ID, clientid);
    }

    public String getGeTuiPushClientId() {
        return sharePreference.read(GE_TUI_PUSH_CLIENT_ID, "");
    }
}
