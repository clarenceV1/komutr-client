package com.komutr.client.ui.main;

import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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
import com.komutr.client.been.User;
import com.komutr.client.common.RouterManager;
import com.komutr.client.dao.UserInfoDao;
import com.komutr.client.databinding.MainBinding;
import com.komutr.client.event.LoginEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.Lazy;

@Route(path = RouterManager.ROUTER_MAIN, name = "首页")
public class MainActivity extends AppBaseActivity<MainBinding> implements MainView, View.OnClickListener {

    @Inject
    MainPresenter presenter;

    @Inject
    ILoadImage iLoadImage;


    @Inject
    Lazy<UserInfoDao> userInfoDao;

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

        mViewBinding.ivSelf.setOnClickListener(this);
        mViewBinding.ivWallet.setOnClickListener(this);
        mViewBinding.btnLogin.setOnClickListener(this);
        mViewBinding.btnLogout.setOnClickListener(this);
        mViewBinding.ivUserAvatar.setOnClickListener(this);
        mViewBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        userInfoDao.get().switcher();
        initLeftData(userInfoDao.get().getUser());
    }

    /**
     *
     * @param user
     */
    private void initLeftData(User user) {

        if(user != null){
            mViewBinding.ivUserAvatar.setClickable(true);
            mViewBinding.btnLogin.setVisibility(View.GONE);
            mViewBinding.btnLogout.setVisibility(View.VISIBLE);
            mViewBinding.llUserInfoLayout.setVisibility(View.VISIBLE);
            mViewBinding.tvUserNickName.setText(user.getUsername());
            mViewBinding.tvUserId.setText(getString(R.string.id,user.getId()+""));
            ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                    .placeholder(R.drawable.default_avatar)
                    .error(R.drawable.default_avatar)
                    .url(user.getAvatar())
                    .transformation(new GlideCircleTransform(this))
                    .build();
            imageParams.setImageView(mViewBinding.ivUserAvatar);
            iLoadImage.loadImage(this, imageParams);
        }else {
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
                break;
            case R.id.btnLogin://登录
                RouterManager.goLogin();
                break;
            case R.id.btnLogout://退出
                 initLeftData(null);
                break;
            case R.id.ivUserAvatar://头像
                RouterManager.goPersonInfo();
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
