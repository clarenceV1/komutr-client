package com.komutr.client.ui.main;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.imageload.GlideCircleTransform;
import com.cai.framework.imageload.ILoadImage;
import com.cai.framework.imageload.ILoadImageParams;
import com.cai.framework.imageload.ImageForGlideParams;
import com.example.clarence.utillibrary.CommonUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.MainBinding;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_MAIN, name = "首页")
public class MainActivity extends AppBaseActivity<MainBinding> implements MainView, View.OnClickListener {

    @Inject
    MainPresenter presenter;

    @Inject
    ILoadImage iLoadImage;

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
//        setBarTitle("wo");
      /*  mViewBinding.tvTitle222.setText(presenter.getTest2());
        mViewBinding.tvTitle222.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterManager.goLogin();
//                RouterManager.goWeb("http://www.baidu.com","百度是我孙子",null);
            }
        });*/
        initLeftView();
        mViewBinding.ivSelf.setOnClickListener(this);
        mViewBinding.ivWallet.setOnClickListener(this);
        mViewBinding.btnLogin.setOnClickListener(this);
        mViewBinding.btnLogout.setOnClickListener(this);
        mViewBinding.ivUserAvatar.setOnClickListener(this);

        ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                .url("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1536463927&di=1fe7a492f833960e4eddd2e968bc8b32&imgtype=jpg&er=1&src=http%3A%2F%2Fww2.sinaimg.cn%2Fbmiddle%2F0062Tsskjw1ev5k66vo12j30c80gbt9w.jpg")
                .transformation(new GlideCircleTransform(this))
                .build();
        imageParams.setImageView(mViewBinding.ivUserAvatar);
        iLoadImage.loadImage(this, imageParams);
    }

    /**
     * 动态添加侧滑列表试图
     */
    private void initLeftView() {
        int height = DimensUtils.dp2px(this, 40f);
        int padding = DimensUtils.dp2px(this, 32f);
        String[] selfList = getResources().getStringArray(R.array.main_item_list);
        int length = selfList.length;
        for (int i = 0; i < length; i++) {
            LinearLayout llItemLayout = new LinearLayout(this);
            llItemLayout.setOrientation(LinearLayout.HORIZONTAL);
            CommonUtils.setBackground(llItemLayout, CommonUtils.selectorStateColor(this, R.color.white, R.color.color_f1f1f4));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            params.gravity = Gravity.CENTER_VERTICAL;
            mViewBinding.llLeftListLayout.addView(llItemLayout, params);
            llItemLayout.setGravity(Gravity.CENTER_VERTICAL);
            llItemLayout.setPadding(padding,0,0,0);

            llItemLayout.setOnClickListener(new OnClickListener(i));
            TextView tvName = new TextView(this);
//            tvName.setPadding(dp13, dp13, dp13, dp13);
            tvName.setTextSize(14);
            tvName.setGravity(Gravity.CENTER_VERTICAL);
            tvName.setText(selfList[i]);
            tvName.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_000000, this));
//            tvName.setCompoundDrawablePadding(dp13 / 2);
//            tvName.setCompoundDrawablesWithIntrinsicBounds(StreamUtils.getInstance().resourceToDrawable(CPResourceUtils.getDrawableId("self_list_" + (i + 1))), null, null, null);
            llItemLayout.addView(tvName);

        }
    }


    /**
     * 动态添加控件
     */
    private void dynamicAddWidget() {


    }

    @Override
    public int getLayoutId() {
        return R.layout.main;
    }

    @Override
    public void tast(String msg) {
        ToastUtils.showShort(msg);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivSelf://我的
                if (mViewBinding.drawerLayout.isDrawerOpen(mViewBinding.llMainLeftLayout)) {
                    mViewBinding.drawerLayout.closeDrawer(mViewBinding.llMainLeftLayout);
                } else {
                    mViewBinding.drawerLayout.openDrawer(mViewBinding.llMainLeftLayout);
                }
                break;
            case R.id.ivWallet://钱包
                break;
            case R.id.btnLogin://登录
                RouterManager.goLogin();
                break;
            case R.id.btnLogout://退出
                break;
            case R.id.ivUserAvatar://头像
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
