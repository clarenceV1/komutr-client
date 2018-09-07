package com.komutr.client.ui.recharge;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.RECHARGE, name = "我的-我的钱包-充值")
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
        dynamicAddWidget();
        mViewBinding.ivBack.setOnClickListener(this);
        mViewBinding.tvBill.setOnClickListener(this);
    }


    /**
     * 动态添加控件
     */
    private void dynamicAddWidget() {


        int dp10 = DimensUtils.dp2px(this, 10f);
        String[] amountList = getResources().getString(R.string.recharge_amount_list).split(",");
        int length = amountList.length;
        ColorStateList colorStateList = CommonUtils.selectorColorState(StreamUtils.getInstance().resourceToColor(R.color.color_ffffff, this), StreamUtils.getInstance().resourceToColor(R.color.color_000000, this));
        Drawable normalD = CommonUtils.getGradientDrawable(StreamUtils.getInstance().resourceToColor(R.color.color_c6c6c6, this), StreamUtils.getInstance().resourceToColor(R.color.transparent, this), dp10 / 10, dp10 / 3);
        Drawable pressedD = CommonUtils.getGradientDrawable(StreamUtils.getInstance().resourceToColor(R.color.transparent, this), StreamUtils.getInstance().resourceToColor(R.color.color_main, this), 0, dp10 / 3);

        for (int i = 0; i < length; i++) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setTextSize(14);
            radioButton.setGravity(Gravity.CENTER);
            radioButton.setText(amountList[i]);
            radioButton.setButtonDrawable(R.color.transparent);
            radioButton.setPadding(dp10, dp10 / 2, dp10, dp10 / 2);
            radioButton.setTextColor(colorStateList);
            radioButton.setGravity(Gravity.CENTER);
            CommonUtils.setBackground(radioButton, CommonUtils.selectorState(normalD, pressedD));
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(dp10 * 10, dp10 * 4);
            params.leftMargin = (i == 0 ? dp10 : dp10 / 2);
            params.rightMargin = (i == length - 1 ? dp10 : dp10 / 2);
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
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvBill://账单

                break;

        }
    }


}
