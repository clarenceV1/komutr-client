package com.komutr.client.ui.main;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.imageload.GlideCircleTransform;
import com.cai.framework.imageload.ILoadImage;
import com.cai.framework.imageload.ILoadImageParams;
import com.cai.framework.imageload.ImageForGlideParams;
import com.cai.framework.widget.dialog.GodDialog;
import com.example.clarence.utillibrary.CommonUtils;
import com.example.clarence.utillibrary.DeviceUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.BuildConfig;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Service;
import com.komutr.client.been.User;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.MainBinding;
import com.komutr.client.event.LoginEvent;
import com.komutr.client.ui.main.fragment.book.BookFragment;
import com.komutr.client.ui.main.fragment.trips.MyTripsFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_MAIN, name = "首页")
public class MainActivity extends AppBaseActivity<MainBinding> implements MainView, View.OnClickListener {

    @Inject
    MainPresenter presenter;
    @Inject
    ILoadImage iLoadImage;

    RadioButton lastFooterRadioButton;
    Service service;

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
//        setBarTitle("首页");
        EventBus.getDefault().register(this);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//防止布局被顶上去
        dynamicAddLeftListView();

        List<Fragment> mTabs = new ArrayList<>();
        mTabs.add(BookFragment.newInstance());
        mTabs.add(MyTripsFragment.newInstance());
        mViewBinding.mainActivityViewpager.setOffscreenPageLimit(2);
        MainPagerAdapter mAdapter = new MainPagerAdapter(getSupportFragmentManager(), mTabs);
        mViewBinding.mainActivityViewpager.setAdapter(mAdapter);
        mViewBinding.mainActivityViewpager.setCurrentItem(0);
        setHeaderTabSelected(mViewBinding.rbMainTabOne, true);
        mViewBinding.ivSelf.setOnClickListener(this);
        mViewBinding.ivWallet.setOnClickListener(this);
        mViewBinding.btnLogin.setOnClickListener(this);
        mViewBinding.btnLogout.setOnClickListener(this);
        mViewBinding.ivUserAvatar.setOnClickListener(this);
        mViewBinding.rbMainTabOne.setOnClickListener(this);
        mViewBinding.tvMainTabTwo.setOnClickListener(this);
        mViewBinding.rbMainTabThree.setOnClickListener(this);


        mViewBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mViewBinding.drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                setStatuBarColor(R.color.color_main);
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                setStatuBarColor(R.color.color_00ffffff);
                super.onDrawerClosed(drawerView);
            }
        });
        User user = presenter.switcher();
        initLeftData(user);
        presenter.requestPhoneAndAppVersion();
    }

    /**
     * @param user
     */
    private void initLeftData(User user) {

        if (user != null) {
            mViewBinding.ivUserAvatar.setClickable(true);
            mViewBinding.btnLogin.setVisibility(View.GONE);
            mViewBinding.btnLogout.setVisibility(View.VISIBLE);
            mViewBinding.llUserInfoLayout.setVisibility(View.VISIBLE);
            mViewBinding.tvUserNickName.setText(user.getUsername());
            mViewBinding.tvUserId.setText(getString(R.string.id, user.getId() + ""));
            ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                    .placeholder(R.drawable.default_avatar)
                    .error(R.drawable.default_avatar)
                    .url(user.getAvatar())
                    .transformation(new GlideCircleTransform(this))
                    .build();
            imageParams.setImageView(mViewBinding.ivUserAvatar);
            iLoadImage.loadImage(this, imageParams);
        } else {
            mViewBinding.ivUserAvatar.setClickable(false);
            mViewBinding.btnLogin.setVisibility(View.VISIBLE);
            mViewBinding.btnLogout.setVisibility(View.GONE);
            mViewBinding.llUserInfoLayout.setVisibility(View.GONE);
        }
    }


    /**
     * 动态添加侧滑列表试图
     */
    private void dynamicAddLeftListView() {
        int height = DimensUtils.dp2px(this, 40f);
        int padding = DimensUtils.dp2px(this, 32f);
        String[] selfList = getResources().getString(R.string.main_item_list).split(",");
        int length = selfList.length;
        for (int i = 0; i < length; i++) {
            LinearLayout llItemLayout = new LinearLayout(this);
            llItemLayout.setOrientation(LinearLayout.HORIZONTAL);
            CommonUtils.setBackground(llItemLayout, CommonUtils.selectorStateColor(this, R.color.white, R.color.color_f1f1f4));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
            params.gravity = Gravity.CENTER_VERTICAL;
            mViewBinding.llLeftListLayout.addView(llItemLayout, params);
            llItemLayout.setGravity(Gravity.CENTER_VERTICAL);
            llItemLayout.setPadding(padding, 0, 0, 0);

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
        addVListener();
    }

    /**
     * 是指位置文字在底部显示
     */
    private void addVListener() {

        mViewBinding.llLeftLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @Override
            public void onGlobalLayout() {
                int screenHeight = DeviceUtils.getScreenHeight(MainActivity.this);
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mViewBinding.tvLocation.getLayoutParams();
                params.topMargin = (screenHeight - mViewBinding.llLeftLayout.getHeight()) - DimensUtils.dp2px(MainActivity.this, 45f);
                mViewBinding.tvLocation.setLayoutParams(params);
                mViewBinding.llLeftLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.main;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(LoginEvent loginEvent) {
        if (loginEvent != null && loginEvent.getStateType() == LoginEvent.STATE_LOGIN_SUCCESS) {
            initLeftData(loginEvent.getUserInfo());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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
                if (presenter.isLogin()) {
                    RouterManager.goWallet();
                } else {
                    RouterManager.goLogin();
                }
                break;
            case R.id.btnLogin://登录
                RouterManager.goLogin();
                break;
            case R.id.btnLogout://退出
                logoutDialog();
                break;
            case R.id.ivUserAvatar://头像
                if (presenter.isLogin()) {
                    RouterManager.goPersonInfo();
                } else {
                    RouterManager.goLogin();
                }
                break;
            case R.id.rbMainTabOne://地图
                if (mViewBinding.mainActivityViewpager.getCurrentItem() != 0) {
                    setHeaderTabSelected(mViewBinding.rbMainTabOne, false);
                    mViewBinding.mainActivityViewpager.setCurrentItem(0);
                }
                break;
            case R.id.tvMainTabTwo://扫描支付
                  presenter.startGoScanPayPage(this);
                break;
            case R.id.rbMainTabThree://我的行程
                if (mViewBinding.mainActivityViewpager.getCurrentItem() != 1) {
                    setHeaderTabSelected(mViewBinding.rbMainTabThree, false);
                    mViewBinding.mainActivityViewpager.setCurrentItem(1);
                }
                break;
        }
    }

    private void setHeaderTabSelected(RadioButton btnSelected, boolean isFirst) {
        int dp60 = DimensUtils.dp2px(this, 60f);
        int dp3 = DimensUtils.dp2px(this, 3f);
        Drawable selectedDrawable = StreamUtils.getInstance().resourceToDrawable(R.drawable.main_header_tab_pressed, this);
        selectedDrawable.setBounds(0, 0, dp60, dp3);
        btnSelected.setChecked(true);
        btnSelected.setCompoundDrawables(null, null, null, selectedDrawable);

        Drawable normalDrawable = StreamUtils.getInstance().resourceToDrawable(R.drawable.main_header_tab_normal, this);
        normalDrawable.setBounds(0, 0, dp60, dp3);
        if (lastFooterRadioButton != null) {
            lastFooterRadioButton.setCompoundDrawables(null, null, null, normalDrawable);
            lastFooterRadioButton.setChecked(false);
        }
        lastFooterRadioButton = btnSelected;
        if (isFirst) {
            mViewBinding.rbMainTabThree.setChecked(false);
            mViewBinding.rbMainTabThree.setCompoundDrawables(null, null, null, normalDrawable);
        }
    }

    @Override
    public void logout(RespondDO respondDO) {
        if (respondDO.isStatus()) {
            initLeftData(null);
        }
    }

    @Override
    public void serviceCallBack(RespondDO<Service> respondDO) {
        if (respondDO.isStatus()) {
            service = respondDO.getObject();
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
                    if (presenter.isLogin()) {
                        RouterManager.goMessage();
                    } else {
                        RouterManager.goLogin();
                    }
                    break;
                case 1://Service tel
                    if (service != null) {
                        presenter.callPhone(MainActivity.this, service.getTelphone());
                    }
                    break;
                case 2:// Help center
                    RouterManager.goHelpCenter();
                    break;
                case 3://About us
                    RouterManager.goAboutUs();
                    break;
                case 4://Current version
                    handleVersion();
                    break;
            }

        }
    }

    private void handleVersion() {
        if (service != null && service.getApp() != null && !BuildConfig.VERSION_NAME.equals(service.getApp().getVersion())) {//有新版本
            new GodDialog.Builder(MainActivity.this)
                    .setTitle(getString(R.string.detected_latest_version))
                    .setMessage(getString(R.string.update_app_content, service.getApp().getVersion()))
                    .setNegativeButton(R.string.btn_cancle, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton(R.string.btn_update, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            presenter.openBrowser(service.getApp().getUpdate_url());
                            dialog.dismiss();
                        }
                    }).build().show();
        } else { //已经是最新的
            ToastUtils.showShort(R.string.already_latest_version);
        }
    }

    private void logoutDialog() {
        new GodDialog.Builder(MainActivity.this)
                .setTitle(getString(R.string.logout_dialog_title))
                .setNegativeButton(R.string.btn_no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(R.string.btn_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.logout();
                        dialog.dismiss();
                    }
                }).build().show();
    }
}
