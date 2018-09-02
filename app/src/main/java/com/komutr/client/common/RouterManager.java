package com.komutr.client.common;

import com.alibaba.android.arouter.launcher.ARouter;

public class RouterManager {
    public static final String ROUTER_HOME = "/komutr/";
    public static final String ROUTER_WEB = ROUTER_HOME + "WebActivity";
    public static final String ROUTER_MAIN = ROUTER_HOME + "MainActivity";
    public static final String ROUTER_LOGIN = ROUTER_HOME + "LoginActivity";
    public static final String ROUTER_MESSAGE = ROUTER_HOME + "MessageActivity";
    public static final String ROUTER_MESSAGE_DETAIL = ROUTER_HOME + "MessageDetailActivity";
    public static final String ROUTER_FEEDBACK = ROUTER_HOME + "FeedbackActivity";
    public static final String ROUTER_REGION = ROUTER_HOME + "RegionActivity";
    public static final String ROUTER_WALLET = ROUTER_HOME + "WalletActivity";
    public static final String ROUTER_NICKNAME= ROUTER_HOME + "NicknameActivity";

    public static void goWeb(String url, String title, String paramMap) {
        ARouter.getInstance().build(RouterManager.ROUTER_WEB)
                .withString("url", url)
                .withString("title", title)
                .withString("paramMap", paramMap).navigation();
    }

    public static void goLogin() {
        ARouter.getInstance().build(RouterManager.ROUTER_LOGIN).navigation();
    }
    public static void goNickname() {
        ARouter.getInstance().build(RouterManager.ROUTER_NICKNAME).navigation();
    }
}
