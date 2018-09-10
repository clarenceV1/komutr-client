package com.komutr.client.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cai.framework.utils.CPResourceUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.R;


/**
 * 等待框
 *
 * @author Administrator
 */
public class LoadingDialog extends Dialog {

    TextView tvMsg;
    Activity activity;

    ImageView ivLoading;

    TextView tvReloading;

    public LoadingDialog(Activity activity, boolean cancel, String loadingText, final View.OnClickListener onClickListener) {

        super(activity, R.style.dialog_style_theme);
        setContentView(R.layout.dialog_loading);
        this.activity = activity;
        tvMsg = findViewById(R.id.tv_dialog_content);
        ivLoading = findViewById(R.id.iv_dialog_anim);
        tvReloading = findViewById(R.id.tv_dialog_reloading_text);
//        Glide.with(activity).load(R.drawable.loading).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(ivLoading);

        playAnimation();
        if (!StringUtils.isEmpty(loadingText))
            tvMsg.setText(loadingText);
        tvReloading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvReloading.setVisibility(View.GONE);
                tvMsg.setVisibility(View.VISIBLE);
                ivLoading.setVisibility(View.VISIBLE);
                if (onClickListener != null)
                    onClickListener.onClick(view);
            }
        });
        setCanceledOnTouchOutside(cancel);
        setCancelable(cancel);
        show();
    }

    public void onLoadError() {
        ivLoading.setVisibility(View.GONE);
        tvMsg.setVisibility(View.GONE);
        tvReloading.setVisibility(View.VISIBLE);
    }


    private void playAnimation() {

        AnimationDrawable animationDrawable = new AnimationDrawable();
        for (int i = 0; i < 31; i++) {
            animationDrawable.addFrame(StreamUtils.getInstance().resourceToDrawable(CPResourceUtils.getDrawableId("loading_" + i),activity), 1000 / 62);
        }
        animationDrawable.setOneShot(false);//true则只运行一次，false可以循环
        ivLoading.setImageDrawable(animationDrawable);
        animationDrawable.start();

    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
    }


    public void setText(String progressMsg) {
        if (tvMsg != null)
            tvMsg.setText(progressMsg);
    }

    public void dismissDialog(Dialog dialog) {
        if (activity != null && !activity.isFinishing() && dialog.isShowing())
            dialog.dismiss();
    }

}
