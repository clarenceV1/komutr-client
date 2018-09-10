package com.komutr.client.event;

public class EventPostInfo {
    //更新用户信息
    public static final int UPDATE_PERSON_INFO_SUCCESS = 1;

    public static final int ORDER_DELETE_SUCCESS = 2;

    private int stateType;

    public EventPostInfo(int stateType) {
        this.stateType = stateType;

    }

    public int getStateType() {
        return stateType;
    }
}
