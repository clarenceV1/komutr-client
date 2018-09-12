package com.komutr.client.event;

public class EventPostInfo {
    //更新用户信息
    public static final int UPDATE_PERSON_INFO_SUCCESS = 1;
    //个人中心刷新数据
    public static final int REFRESH_PERSON_INFO_SUCCESS = 3;
    //账单删除成功
    public static final int BILL_DELETE_SUCCESS = 2;

    //订单删除成功
    public static final int ORDER_DELETE_SUCCESS = 4;


    //刷新我的行程列表
    public static final int REFRESH_MY_TRIPS = 5;


    private int stateType;

    public EventPostInfo(int stateType) {
        this.stateType = stateType;

    }

    public int getStateType() {
        return stateType;
    }
}
