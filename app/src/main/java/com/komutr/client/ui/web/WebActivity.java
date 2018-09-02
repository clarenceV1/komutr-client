package com.komutr.client.ui.web;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;
import com.cai.framework.web.WebViewFragment;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.WebBinding;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_WEB, name = "web")
public class WebActivity extends AppBaseActivity<WebBinding> implements WebForRTB {

    @Inject
    WebPresenter presenter;
    @Autowired(name = "url")
    String url;
    @Autowired(name = "title")
    String title;
    @Autowired(name = "paramMap")
    String paramString;

    Map<String, String> paramMap;
    WebViewFragment webViewFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            rootView.setBackgroundResource(com.cai.framework.R.color.white_a);
        }
    }

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List observerList) {
        observerList.add(presenter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.web;
    }

    @Override
    public void initView() {
        initTitleBar();
        if (!TextUtils.isEmpty(paramString)) {
            paramMap = JSONObject.parseObject(paramString, HashMap.class);
        }
        initUrl();
        initFragment();
    }

    private void initTitleBar() {
        if (!TextUtils.isEmpty(title)) {
            mViewBinding.titleBar.setTitleText(title);
        } else {
            mViewBinding.titleBar.hideTitle();
        }

        mViewBinding.titleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void initUrl() {
        String paramSt = "";
        if (paramMap != null && paramMap.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                stringBuilder.append(entry.getKey());
                stringBuilder.append("=");
                stringBuilder.append(entry.getValue());
                stringBuilder.append("&");
            }
            int length = stringBuilder.length();
            paramSt = stringBuilder.subSequence(0, length - 1).toString();
        }
        if (!TextUtils.isEmpty(paramSt)) {
            if (url.contains("?")) {
                url = url + "&" + paramSt;
            } else {
                url = url + "?" + paramSt;
            }
        }
        Log.d("WebViewActivity", "url=" + url);
    }

    private void initFragment() {
        Bundle bundle = new Bundle();
        bundle.putString(WebViewFragment.KEY_RUL, url);
        webViewFragment = (WebViewFragment) Fragment.instantiate(this, WebViewFragment.class.getName(), bundle);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containLayout, webViewFragment);
        fragmentTransaction.commit();
    }


    private void goBack() {
        if (webViewFragment != null && webViewFragment.canGoBack()) {
            webViewFragment.goBack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            goBack();
            return true;
        }
        return false;
    }

    @Override
    public Map<String, String> getParams(int requestFlag) {
        return null;
    }

    @Override
    public void onBegin() {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void showToastMsg(String msg) {

    }
}
