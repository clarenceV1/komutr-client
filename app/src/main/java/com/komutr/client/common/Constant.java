package com.komutr.client.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.komutr.client.base.App;


public class Constant {
    public static boolean IS_DEBUG = true;
    public static final String OFFICIAL_BASE_URL = "http://car.xinjuhao.wang/";
    public static final String TEST_BASE_URL = "http://car.xinjuhao.wang/";
    private static final String IS_TEST_ENV = "is_test_env";//是否是测试环境

    public static final String AUTH_KEY = "d511D54i5Odb6WT";

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


    public interface RrequestFlag{


        int FLAG_1 = 1;

        int FLAG_2 = 2;

        int FLAG_3 = 3;

        int FLAG_4 = 4;

        int FLAG_5 = 5;

        int FLAG_6 = 6;

    }
}
