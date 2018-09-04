package com.komutr.client.base;


import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cai.framework.base.GodBasePresenterActivity;
import com.cai.framework.baseview.TitleBarView;
import com.komutr.client.R;
import com.komutr.client.common.Constant;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by clarence on 2018/1/12.
 */

public abstract class AppBaseActivity<M extends ViewDataBinding> extends GodBasePresenterActivity<M> {
    TitleBarView titleBarView;

    @Override
    public void setStatusBar(SystemBarTintManager tintManager) {
        tintManager.setTintColor(getResources().getColor(R.color.transparent));
    }

    public void setStatuBarColor(int color) {
        if (tintManager != null) {
            tintManager.setTintColor(getResources().getColor(color));
        }
    }

    public void setBarTitle(String title) {
        if (rootView != null && rootView.getChildCount() > 0) {
            View view = rootView.getChildAt(0);
            if (view != null && view instanceof LinearLayout) {
                LinearLayout linearLayout = (LinearLayout) view;
                titleBarView = new TitleBarView(this);
                titleBarView.setBackgroundResource(R.color.color_main);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout.addView(titleBarView, 0, layoutParams);
                titleBarView.setTitleText(title);
                titleBarView.setLeftClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        }
    }


    public Map<String, String> getUserParams() {

        Map<String, String> query = new HashMap<>();
        query.put("auth_key", Constant.AUTH_KEY);
        return query;
    }
}
