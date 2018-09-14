package com.komutr.client.ui.aboutUs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.web.WebViewFragment;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.AboutUs;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.AboutBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_ABOUT_US, name = "我的-关于我们")
public class AboutUsActivity extends AppBaseActivity<AboutBinding> implements AboutUsView {

    @Inject
    AboutUsPresenter presenter;

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
    }

    @Override
    public void initView() {
        setBarTitle(getString(R.string.about_us_title));
        showDialog(getString(R.string.loading),true);
        presenter.requestContent();
    }

    @Override
    public int getLayoutId() {
        return R.layout.about;
    }

    @Override
    public void callback(RespondDO<AboutUs> respondDO) {
        hiddenDialog();
        if(respondDO.isStatus() && respondDO.getObject() != null && respondDO.getObject().getContent() != null){
            initFragment(respondDO.getObject().getContent().getContent());
        }
    }


    private void initFragment(String htmlData) {
        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.HTML_DATA, htmlData);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.llCenterAboutLayout, Fragment.instantiate(this, WebViewFragment.class.getName(), bundle));
        fragmentTransaction.commit();
    }
}
