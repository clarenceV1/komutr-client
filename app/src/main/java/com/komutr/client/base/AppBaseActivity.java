package com.komutr.client.base;


import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.cai.framework.base.GodBasePresenterActivity;
import com.cai.framework.baseview.TitleBarView;
import com.komutr.client.R;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.common.Constant;
import com.komutr.client.ui.dialog.LoadingDialog;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by clarence on 2018/1/12.
 */

public abstract class AppBaseActivity<M extends ViewDataBinding> extends GodBasePresenterActivity<M> {
    public TitleBarView titleBarView;

    @Override
    public void setStatusBar(SystemBarTintManager tintManager) {
        tintManager.setTintColor(getResources().getColor(R.color.color_main));
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

    protected <T> T getArouterSerializableData(String key) {
        Intent intent = getIntent();
        if (intent != null) {
            Serializable serializable = intent.getSerializableExtra(key);
            return (T) serializable;
        }
        return null;
    }

    public Map<String, String> getUserParams() {

        Map<String, String> query = new HashMap<>();
        query.put("auth_key", Constant.AUTH_KEY);
        return query;
    }


    LoadingDialog loadingDialog;

    public void showDialog(String content, boolean cancel) {

        if (loadingDialog == null || !loadingDialog.isShowing())
            loadingDialog = new LoadingDialog(this, cancel, content, null);
        else {
            loadingDialog.setText(content);
        }
    }

    public void hiddenDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismissDialog(loadingDialog);
            loadingDialog = null;
        }
    }


    public void onShowLoadDialog(String content,View.OnClickListener onClickListener) {
        if (loadingDialog == null || !loadingDialog.isShowing())
            loadingDialog = new LoadingDialog(this, true, content, onClickListener);
    }

    public void onShowLoadError() {
        if (loadingDialog != null)
            loadingDialog.onLoadError();
    }
}
