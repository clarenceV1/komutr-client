package com.komutr.client.ui.personInfo;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
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
import com.komutr.client.databinding.PersonInfoBinding;
import com.komutr.client.databinding.WalletBinding;
import com.komutr.client.ui.wallet.WalletPresenter;
import com.komutr.client.ui.wallet.WalletView;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.PERSON_INFO, name = "我的-个人信息")
public class PersonInfoActivity extends AppBaseActivity<PersonInfoBinding> implements PersonInfoView {

    @Inject
    PersonInfoPresenter presenter;

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
        setBarTitle(getString(R.string.person_info));
        dynamicAddWidget();
    }


    /**
     * 动态添加控件
     */
    private void dynamicAddWidget() {
        int height = DimensUtils.dp2px(this, 45f);
        int padding = DimensUtils.dp2px(this, 16f);
        String[] personInfoList = getResources().getStringArray(R.array.person_info_item_list);
        int length = personInfoList.length;
        for (int i = 0; i < length; i++) {
            LinearLayout llItemLayout = new LinearLayout(this);
            llItemLayout.setOrientation(LinearLayout.HORIZONTAL);
            CommonUtils.setBackground(llItemLayout, CommonUtils.selectorStateColor(this, R.color.transparent, R.color.color_f1f1f4));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            params.gravity = Gravity.CENTER_VERTICAL;
            mViewBinding.llPersonInfoLayout.addView(llItemLayout, params);
            llItemLayout.setGravity(Gravity.CENTER_VERTICAL);
            llItemLayout.setPadding(padding, 0, padding, 0);

            llItemLayout.setOnClickListener(new OnClickListener(i));
            TextView tvName = new TextView(this);

            tvName.setTextSize(15f);
            tvName.setGravity(Gravity.CENTER_VERTICAL);
            tvName.setText(personInfoList[i]);
            tvName.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_000000, this));
            llItemLayout.addView(tvName);

            tvName = new TextView(this);
            tvName.setTextSize(15f);
            tvName.setGravity(Gravity.CENTER_VERTICAL);
            tvName.setGravity(Gravity.RIGHT);
            tvName.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_666666, this));
//            tvName.setText(i == 3 ? SharedPreUtils.getString(Constant.SharedPreferKey.SERVICE_TEL) : "");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.weight = 1;
            layoutParams.width = 0;
            llItemLayout.addView(tvName, layoutParams);
            View view = new View(this);
            view.setBackgroundResource(R.color.color_f1f1f4);
            LinearLayout.LayoutParams viewP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
            viewP.leftMargin = padding;
            viewP.rightMargin = padding;
            mViewBinding.llPersonInfoLayout.addView(view, viewP);
            tvName.setCompoundDrawablesWithIntrinsicBounds(null, null, StreamUtils.getInstance().resourceToDrawable(R.drawable.right_arrow_icon, this), null);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.person_info;
    }


    //item的点击事件
    class OnClickListener implements View.OnClickListener {

        int index;

        public OnClickListener(int index) {
            this.index = index;
        }


        @Override
        public void onClick(View view) {

            switch (index) {
                case 0://Message
                    break;
                case 1://Service tel
                    break;
                case 2:// Help center
                    break;
                case 3://About us
                    break;
                case 4://Current version
                    break;
            }

        }
    }

}
