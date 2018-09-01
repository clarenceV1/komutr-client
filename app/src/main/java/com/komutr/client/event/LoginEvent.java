package com.komutr.client.event;

import com.komutr.client.been.UserInfo;

public class LoginEvent {
    public static final int STATE_LOGIN_SUCCESS = 1;
    public static final int STATE_LOGIN_OUT_SUCCESS = 2;

    private int stateType;
    private UserInfo userInfo;

    public LoginEvent(int stateType, UserInfo userInfo) {
        this.stateType = stateType;
        this.userInfo = userInfo;
    }
}
