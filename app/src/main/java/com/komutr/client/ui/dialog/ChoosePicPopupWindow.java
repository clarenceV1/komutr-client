package com.komutr.client.ui.dialog;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.clarence.utillibrary.CommonUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.PhotoUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.komutr.client.R;
import com.komutr.client.ui.personInfo.PersonInfoPresenter;


/**
 * 选择图片弹窗
 * Created by chengaobin on 2018/9/11.
 */
public class ChoosePicPopupWindow extends PopupWindow {

    FragmentActivity activity;


    public ChoosePicPopupWindow(FragmentActivity activity, AttributeSet attrs) {
        super(activity, attrs);
        this.activity = activity;
        initView();
    }

    PersonInfoPresenter presenter;

    public ChoosePicPopupWindow(FragmentActivity activity, PersonInfoPresenter presenter) {
        super(activity);
        this.activity = activity;
        this.presenter = presenter;
        initView();
    }


    private void initView() {
        View view = activity.getLayoutInflater().inflate(R.layout.choose_pic_pop_layout, null);
        TextView tvPopChange = view.findViewById(R.id.tv_camera);
        TextView tvPopDelete = view.findViewById(R.id.tv_album);
        TextView tvCancel = view.findViewById(R.id.tv_cancel);


        int dp5 = DimensUtils.dp2px(activity, 5f);
        setBg(tvPopChange, new float[]{dp5, dp5, dp5, dp5, 0, 0, 0, 0});
        setBg(tvPopDelete, new float[]{0, 0, 0, 0, dp5, dp5, dp5, dp5});

        //设置LabelPopupWindow的View
        this.setContentView(view);
        //设置LabelPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置LabelPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置LabelPopupWindow弹出窗体可点击
        this.setInputMethodMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.setOutsideTouchable(true);
        this.setTouchable(true);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(StreamUtils.getInstance().resourceToColor(R.color.color_333333,activity));
        this.setBackgroundDrawable(dw);
//        this.setBackgroundDrawable(new ColorDrawable(0));
        this.getBackground().setAlpha(123);
        this.setAnimationStyle(R.style.dialogWindowAnim);
        this.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        this.update();
        //----------------绑定监听器----------
        tvPopChange.setOnClickListener(new LockOnClickListener());
        tvPopDelete.setOnClickListener(new LockOnClickListener());
        tvCancel.setOnClickListener(new LockOnClickListener());
    }


    private void setBg(View view, float[] radii) {
        Drawable normalDrawable = CommonUtils.getGradientDrawable(radii, StreamUtils.getInstance().resourceToColor(R.color.white, activity), StreamUtils.getInstance().resourceToColor(R.color.transparent, activity), 0);
        Drawable pressedDrawable = CommonUtils.getGradientDrawable(radii, StreamUtils.getInstance().resourceToColor(R.color.color_f1f1f4, activity), StreamUtils.getInstance().resourceToColor(R.color.transparent, activity), 0);
        CommonUtils.setBackground(view, CommonUtils.selectorState(normalDrawable, pressedDrawable));
    }

    class LockOnClickListener implements View.OnClickListener {


        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.tv_camera:
                    if (presenter != null)
                        presenter.startCamera(activity);
                    break;
                case R.id.tv_album:
                    if (presenter != null)
                        presenter.selectAlbum(activity);
                    break;
            }
            dismiss();
        }
    }

}
