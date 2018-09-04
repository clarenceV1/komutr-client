package com.komutr.client.common;

import com.alibaba.android.arouter.launcher.ARouter;
import com.komutr.client.ui.payment.ConfirmPayActivity;

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
    public static final String ROUTER_NICKNAME = ROUTER_HOME + "NicknameActivity";
    public static final String SEARCH_ROUTES = ROUTER_HOME + "SearchRoutesActivity";
    public static final String ROUTER_PERSON_INFO = ROUTER_HOME + "PersonInfoActivity";
    public static final String ROUTER_REPLACE_PHONE = ROUTER_HOME + "ReplacePhoneActivity";
    public static final String ROUTER_BIND_PHONE = ROUTER_HOME + "BindPhoneActivity";
    public static final String ROUTER_CONFIRM_PAY = ROUTER_HOME + "ConfirmPayActivity";



    public static void goWeb(String url, String title, String paramMap) {
        ARouter.getInstance().build(RouterManager.ROUTER_WEB)
                .withString("url", url)
                .withString("title", title)
                .withString("paramMap", paramMap).navigation();
    }
    public static void goConfirmPay() {
        ARouter.getInstance().build(RouterManager.ROUTER_CONFIRM_PAY).navigation();
    }
    public static void goBindPhone() {
        ARouter.getInstance().build(RouterManager.ROUTER_BIND_PHONE).navigation();
    }
    public static void goReplacePhone() {
        ARouter.getInstance().build(RouterManager.ROUTER_REPLACE_PHONE).navigation();
    }

    public static void goLogin() {
        ARouter.getInstance().build(RouterManager.ROUTER_LOGIN).navigation();
    }

    public static void goNickname() {
        ARouter.getInstance().build(RouterManager.ROUTER_NICKNAME).navigation();
    }

    public static void goPersonInfo() {
        ARouter.getInstance().build(RouterManager.ROUTER_PERSON_INFO).navigation();
    }

    public static void goFeedback() {
        ARouter.getInstance().build(RouterManager.ROUTER_FEEDBACK).navigation();
    }


    public static void goMessage() {
        ARouter.getInstance().build(RouterManager.ROUTER_MESSAGE).navigation();
    }

    public static void goRegion() {
        ARouter.getInstance().build(RouterManager.ROUTER_REGION).navigation();
    }
}
