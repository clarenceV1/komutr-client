package com.komutr.client.dagger.component;


import com.cai.framework.dagger.ActivityScope;
import com.komutr.client.base.App;
import com.komutr.client.dagger.module.CommonModule;
import com.komutr.client.ui.feedback.FeedbackActivity;
import com.komutr.client.ui.login.LoginActivity;
import com.komutr.client.ui.main.MainActivity;
import com.komutr.client.ui.main.fragment.book.BookFragment;
import com.komutr.client.ui.main.fragment.empty.EmptyFragment;
import com.komutr.client.ui.main.fragment.trips.MyTripsFragment;
import com.komutr.client.ui.message.MessageActivity;
import com.komutr.client.ui.message.MessageDetailActivity;
import com.komutr.client.ui.nickname.NicknameActivity;
import com.komutr.client.ui.payment.ConfirmPayActivity;
import com.komutr.client.ui.personInfo.PersonInfoActivity;
import com.komutr.client.ui.phoneNumber.BindPhoneActivity;
import com.komutr.client.ui.phoneNumber.ReplacePhoneActivity;
import com.komutr.client.ui.region.RegionActivity;
import com.komutr.client.ui.routes.SearchRoutesActivity;
import com.komutr.client.ui.wallet.WalletActivity;
import com.komutr.client.ui.web.WebActivity;

import dagger.Component;

/**
 * Created by clarence on 2018/3/26
 */
@ActivityScope
@Component(dependencies = CommonComponent.class, modules = CommonModule.class)
public interface AppComponent {

    void inject(App app);
    void inject(MainActivity activity);
    void inject(WebActivity activity);
    void inject(LoginActivity activity);
    void inject(MessageActivity activity);
    void inject(MessageDetailActivity activity);
    void inject(FeedbackActivity activity);
    void inject(RegionActivity activity);
    void inject(WalletActivity activity);
    void inject(NicknameActivity activity);
    void inject(PersonInfoActivity activity);
    void inject(SearchRoutesActivity activity);
    void inject(ReplacePhoneActivity activity);
    void inject(ConfirmPayActivity activity);


    void inject(BindPhoneActivity activity);
    void inject(BookFragment fragment);
    void inject(EmptyFragment fragment);
    void inject(MyTripsFragment fragment);

}
