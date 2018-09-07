package com.komutr.client.common;

import com.alibaba.android.arouter.launcher.ARouter;

public class RouterManager {
    public static final String ROUTER_HOME = "/komutr/";
    public static final String ROUTER_WEB = ROUTER_HOME + "WebActivity";
    public static final String ROUTER_MAIN = ROUTER_HOME + "MainActivity";
    public static final String ROUTER_LOGIN = ROUTER_HOME + "LoginActivity";
    public static final String ROUTER_MESSAGE = ROUTER_HOME + "MessageActivity";
    public static final String ROUTER_FAQ = ROUTER_HOME + "FAQActivity";
    public static final String ROUTER_FEEDBACK = ROUTER_HOME + "FeedbackActivity";
    public static final String ROUTER_REGION = ROUTER_HOME + "RegionActivity";
    public static final String ROUTER_WALLET = ROUTER_HOME + "WalletActivity";
    public static final String ROUTER_NICKNAME = ROUTER_HOME + "NicknameActivity";
    public static final String SEARCH_ROUTES = ROUTER_HOME + "SearchRoutesActivity";
    public static final String ROUTER_PERSON_INFO = ROUTER_HOME + "PersonInfoActivity";
    public static final String ROUTER_REPLACE_PHONE = ROUTER_HOME + "ReplacePhoneActivity";
    public static final String ROUTER_BIND_PHONE = ROUTER_HOME + "BindPhoneActivity";
    public static final String ROUTER_CONFIRM_PAY = ROUTER_HOME + "ConfirmPayActivity";
    public static final String ROUTER_ABOUT_US = ROUTER_HOME + "AboutUsActivity";
    public static final String ROUTER_HELP_CENTER = ROUTER_HOME + "HelpCenterActivity";
    public static final String ROUTER_PHONE_NUMBER = ROUTER_HOME + "PhoneNumberActivity";
    public static final String ROUTER_BIND_EMAIL = ROUTER_HOME + "EmailActivity";
    public static final String ROUTER_BILL_DETAIL = ROUTER_HOME + "BillDetailActivity";

    public static void goBillDetail() {
        ARouter.getInstance().build(RouterManager.ROUTER_BILL_DETAIL).navigation();
    }

    public static void goBindEmail() {
        ARouter.getInstance().build(RouterManager.ROUTER_BIND_EMAIL).navigation();
    }

    public static void goWeb(String url, String title, String paramMap) {
        ARouter.getInstance().build(ROUTER_WEB)
                .withString("url", url)
                .withString("title", title)
                .withString("paramMap", paramMap).navigation();
    }

    public static void goFAQ(int contentType) {
        ARouter.getInstance().build(ROUTER_FAQ)
                .withInt("contentType", contentType).navigation();
    }

    public static void goHelpCenter() {
        ARouter.getInstance().build(RouterManager.ROUTER_HELP_CENTER).navigation();
    }

    public static void goWallet() {
        ARouter.getInstance().build(RouterManager.ROUTER_WALLET).navigation();
    }

    public static void goAboutUs() {
        ARouter.getInstance().build(RouterManager.ROUTER_ABOUT_US).navigation();
    }

    public static void goConfirmPay() {
        ARouter.getInstance().build(RouterManager.ROUTER_CONFIRM_PAY).navigation();
    }

    public static void goBindPhone() {
        ARouter.getInstance().build(ROUTER_BIND_PHONE).navigation();
    }

    public static void goPhoneNumber() {
        ARouter.getInstance().build(ROUTER_PHONE_NUMBER).navigation();

    }

    public static void goReplacePhone() {
        ARouter.getInstance().build(ROUTER_REPLACE_PHONE).navigation();
    }

    public static void goLogin() {
        ARouter.getInstance().build(ROUTER_LOGIN).navigation();
    }

    public static void goNickname() {
        ARouter.getInstance().build(ROUTER_NICKNAME).navigation();
    }

    public static void goPersonInfo() {
        ARouter.getInstance().build(ROUTER_PERSON_INFO).navigation();
    }

    public static void goFeedback() {
        ARouter.getInstance().build(ROUTER_FEEDBACK).navigation();
    }


    public static void goMessage() {
        ARouter.getInstance().build(ROUTER_MESSAGE).navigation();
    }

    public static void goRegion() {
        ARouter.getInstance().build(ROUTER_REGION).navigation();
    }


//--------------------------------你从顶上添加我从下面添加防止冲突----------------------














    public static final String REVIEW_PURCHASE = ROUTER_HOME + "ReviewPurchaseActivity";

    public static final String ROUTE_DETAIL = ROUTER_HOME + "RouteDetailActivity";

    public static final String CONFIRM_PAYMENT = ROUTER_HOME + "ConfirmPaymentActivity";

    public static final String PAY_STATUS = ROUTER_HOME + "StatusActivity";

    public static final String ORDER_DETAILS = ROUTER_HOME + "OrderDetailsActivity";

    public static final String USER_RATINGS = ROUTER_HOME + "UserRatingsActivity";

    public static final String POSITION = ROUTER_HOME + "PositionActivity";

    public static final String RECHARGE = ROUTER_HOME + "RechargeActivity";

    public static final String BILL = ROUTER_HOME + "BillActivity";

    public static void goOrderDetails() {
        ARouter.getInstance().build(ORDER_DETAILS).navigation();
    }

    public static void goPayStatus() {
        ARouter.getInstance().build(PAY_STATUS).navigation();
    }

    public static void goConfirmPayment(String scanContent) {
        ARouter.getInstance().build(CONFIRM_PAYMENT)
                .withString("scanContent", scanContent)
                .navigation();
    }

    public static void goSearchRoutes(String startSite, String endSite) {
        ARouter.getInstance().build(SEARCH_ROUTES)
                .withString("startSite", startSite)
                .withString("endSite", endSite)
                .navigation();
    }

    public static void goReviewPurchase() {
        ARouter.getInstance().build(REVIEW_PURCHASE).navigation();
    }

    public static void goRouteDetail() {
        ARouter.getInstance().build(ROUTE_DETAIL).navigation();
    }

    public static void goMain() {
        ARouter.getInstance().build(ROUTER_MAIN).navigation();
    }

    public static void goUserRatings() {
        ARouter.getInstance().build(USER_RATINGS).navigation();
    }

    public static void goPosition() {
        ARouter.getInstance().build(POSITION).navigation();
    }

    public static void goRecharge() {
        ARouter.getInstance().build(RECHARGE).navigation();
    }


    public static void goBill() {
        ARouter.getInstance().build(BILL).navigation();
    }
}
