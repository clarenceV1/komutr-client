package com.komutr.client.ui.recharge;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.example.clarence.utillibrary.CommonUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.RechargeBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.RECHARGE, name = "我的-我的钱包-充值-账单")
public class RechargeActivity extends AppBaseActivity<RechargeBinding> implements RechargeView, View.OnClickListener {

    @Inject
    RechargePresenter presenter;

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
        setBarTitle(getString(R.string.recharge));
        if (titleBarView != null) {
            titleBarView.setRightText(getString(R.string.bill));
            titleBarView.setRightClickListener(this);
        }
        dynamicAddWidget();
        addRechargeModeView();
        mViewBinding.tvRechargeAgreement.setOnClickListener(this);

    }

    TextView tvCurrentMode;

    /**
     * 添加充值模式选择
     */
    private void addRechargeModeView() {


        int height = DimensUtils.dp2px(this, 50f);
        int padding = DimensUtils.dp2px(this, 15f);
        String[] modeList = getResources().getString(R.string.recharge_mode).split(",");
        int length = modeList.length;
        for (int i = 0; i < length; i++) {

            TextView tvName = new TextView(this);
            tvName.setOnClickListener(new OnClickListener(i));
            tvName.setPadding(padding, 0, padding, 0);
            tvName.setTextSize(15f);
            tvName.setGravity(Gravity.CENTER_VERTICAL);
            tvName.setText(modeList[i]);
            tvName.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_000000, this));
            tvName.setCompoundDrawablePadding(height / 4);
            tvName.setCompoundDrawablesWithIntrinsicBounds(StreamUtils.getInstance().resourceToDrawable(R.drawable.pay_icon,this), null, StreamUtils.getInstance().resourceToDrawable(i==0?R.drawable.checked_icon:R.drawable.unchecked_icon,this), null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            params.gravity = Gravity.CENTER_VERTICAL;
            mViewBinding.llRechargeModeLayout.addView(tvName,params);
        }
        tvCurrentMode = (TextView) mViewBinding.llRechargeModeLayout.getChildAt(0);

    }




    /**
     * 动态添加充值数额控件
     */
    private void dynamicAddWidget() {


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        // 获得手机的宽带和高度像素单位为px
        int widthPixels = dm.widthPixels;

        int dp1 = DimensUtils.dp2px(this, 1f);
        int dp10 = DimensUtils.dp2px(this, 10f);
        int width = (widthPixels - dp10 * 6) / 3;


        String[] amountList = getResources().getString(R.string.recharge_amount_list).split(",");
        int length = amountList.length;
        ColorStateList colorStateList = CommonUtils.selectorColorState(StreamUtils.getInstance().resourceToColor(R.color.color_000000, this), StreamUtils.getInstance().resourceToColor(R.color.color_ffffff, this));
        Drawable normalD = CommonUtils.getGradientDrawable(StreamUtils.getInstance().resourceToColor(R.color.color_c6c6c6, this), StreamUtils.getInstance().resourceToColor(R.color.transparent, this), dp1, dp1 * 3);
        Drawable pressedD = CommonUtils.getGradientDrawable(StreamUtils.getInstance().resourceToColor(R.color.transparent, this), StreamUtils.getInstance().resourceToColor(R.color.color_main, this), 0, dp1 * 3);

        for (int i = 0; i < length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setTextSize(15f);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setText(amountList[i]);
            radioButton.setButtonDrawable(R.color.transparent);
            radioButton.setTextColor(colorStateList);
            radioButton.setGravity(Gravity.CENTER);
            CommonUtils.setBackground(radioButton, CommonUtils.selectorState(normalD, pressedD));
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(width, dp1 * 40);
            params.bottomMargin = dp10;
            int index = i + 1;
            params.leftMargin = (index == 1 || index % 3 == 0+1 ? dp10 * 2 : dp10 / 2);
            params.rightMargin = (index == length || (index != 0 && index % 3 == 0) ? dp10 * 2 : dp10 / 2);

            params.gravity = Gravity.CENTER;
            mViewBinding.xcfAmountLayout.addView(radioButton, params);
        }
        ((RadioButton) mViewBinding.xcfAmountLayout.getChildAt(0)).setChecked(true);

    }

    @Override
    public int getLayoutId() {
        return R.layout.recharge;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case com.cai.framework.R.id.tvRight://账单
                RouterManager. goBill();
                break;
           case R.id.tvRechargeAgreement://充值协议
               break;


        }
    }

    //item的点击事件
    class OnClickListener implements View.OnClickListener {

        int index;

        public OnClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View view) {

            TextView tv = (TextView) view;
            if(!tv.getText().toString().equals(tvCurrentMode.getText().toString())){
                tvCurrentMode.setCompoundDrawablesWithIntrinsicBounds(StreamUtils.getInstance().resourceToDrawable(R.drawable.pay_icon,RechargeActivity.this), null, StreamUtils.getInstance().resourceToDrawable(R.drawable.unchecked_icon,RechargeActivity.this), null);
                tv.setCompoundDrawablesWithIntrinsicBounds(StreamUtils.getInstance().resourceToDrawable(R.drawable.pay_icon,RechargeActivity.this), null, StreamUtils.getInstance().resourceToDrawable(R.drawable.checked_icon,RechargeActivity.this), null);
                tvCurrentMode = tv;
            }
        }
    }


}
