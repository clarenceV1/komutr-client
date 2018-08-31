package com.komutr.client.base;


import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.cai.framework.base.GodBasePresenterActivity;
import com.komutr.client.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by clarence on 2018/1/12.
 */

public abstract class AppBaseActivity<M extends ViewDataBinding> extends GodBasePresenterActivity<M> {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View rootView = findViewById(android.R.id.content);
        if (rootView != null) {
            rootView.setBackgroundResource(com.cai.framework.R.color.white_a);
        }
    }

    @Override
    public void setStatusBar(SystemBarTintManager tintManager) {
        tintManager.setTintColor(getResources().getColor(R.color.transparent));
    }

}
