package com.komutr.client.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.komutr.client.base.App;


public class Constant {
    public static boolean IS_DEBUG = true;

    public static final String OFFICIAL_BASE_URL = "https://more.ethte.com/";
    public static final String TEST_BASE_URL = "http://101.37.146.65/";
    private static final String IS_TEST_ENV = "is_test_env";//是否是测试环境


    public static final String ROUTER_HOME = "/komutr/";
    public static final String ROUTER_WEB = ROUTER_HOME+"WebActivity";
    public static final String ROUTER_MAIN = ROUTER_HOME+"MainActivity";
    public static final String ROUTER_LOGIN = ROUTER_HOME+"LoginActivity";
    public static final String ROUTER_MESSAGE = ROUTER_HOME+"MessageActivity";
    public static final String ROUTER_MESSAGE_DETAIL = ROUTER_HOME+"MessageDetailActivity";
    public static final String ROUTER_FEEDBACK = ROUTER_HOME+"FeedbackActivity";
    public static final String ROUTER_REGION = ROUTER_HOME+"RegionActivity";
    /**
     * 设置是否是测试环境
     *
     * @param isTest
     */
    public static void saveTestEnv(boolean isTest) {
        SharedPreferences preferences = App.getAppContext().getSharedPreferences("default", Context.MODE_PRIVATE);
        preferences.edit().putBoolean(IS_TEST_ENV, isTest).commit();
    }

    /**
     * 设置是否是测试环境
     *
     * @return
     */
    public static boolean isTestEnv() {
        SharedPreferences preferences = App.getAppContext().getSharedPreferences("default", Context.MODE_PRIVATE);
        return preferences.getBoolean(IS_TEST_ENV, false);
    }
}
