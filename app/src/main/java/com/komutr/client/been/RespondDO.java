package com.komutr.client.been;

public class RespondDO<T> {
    //    {
//        "type":"msg",                //类型：String  必有字段  备注：无
//            "status":true,                //类型：Boolean  必有字段  备注：无
//            "code":1111,                //类型：Number  必有字段  备注：无
//            "msg":"user info is update",                //类型：String  必有字段  备注：无
//            "data": - {                //类型：Object  必有字段  备注：无
//            "avatar":"http://127.0.0.1/bus/public//uploads/20180828/a0e6fd10585aaf8cfbba86d7de86fcec_tt.jpg",                //类型：String  必有字段  备注：头像地址
//            "avatar_thum":"http://127.0.0.1/bus/public//uploads/20180828/a0e6fd10585aaf8cfbba86d7de86fcec_tt.jpg"                //类型：String  必有字段  备注：头像略缩图
//    },
//        "time":1535441136                //类型：Number  必有字段  备注：无
//    }
    private String type;
    private boolean status = false;
    private String code = "404";
    private String msg;
    private String data;
    private long time;//有得接口没有返回这个
    private T object;//自己用

    /**
     * -1为异常回调
     */
    private int fromCallBack;


    public int getFromCallBack() {
        return fromCallBack;
    }

    public void setFromCallBack(int fromCallBack) {
        this.fromCallBack = fromCallBack;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
