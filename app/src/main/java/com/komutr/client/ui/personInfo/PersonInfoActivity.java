package com.komutr.client.ui.personInfo;

import android.util.SparseArray;
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
import com.cai.framework.logger.Logger;
import com.example.clarence.utillibrary.CommonUtils;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.User;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.PersonInfoBinding;
import com.komutr.client.event.EventPostInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import com.komutr.client.ui.nickname.NicknamePresenter;
import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_PERSON_INFO, name = "我的-个人信息")
public class PersonInfoActivity extends AppBaseActivity<PersonInfoBinding> implements PersonInfoView {

    @Inject
    PersonInfoPresenter presenter;
    @Inject
    NicknamePresenter nicknamePresenter;
    @Inject
    ILoadImage loadImage;
    SparseArray<String> infoList = new SparseArray<>();

    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List<GodBasePresenter> observerList) {
        observerList.add(presenter);
        observerList.add(nicknamePresenter);
    }

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        setBarTitle(getString(R.string.person_info));
        initData();
        dynamicAddWidget();
        presenter.requestUserInfo();
//        presenter.uploadImage();
    }


    private void initData() {
        User user = presenter.getUserInfo();
        if (user != null) {
            if (!StringUtils.isEmpty(user.getAvatar_thum())) {
                ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                        .url(user.getAvatar_thum())
                        .placeholder(R.drawable.default_avatar)
                        .error(R.drawable.default_avatar)
                        .transformation(new GlideCircleTransform(this))
                        .build();
                imageParams.setImageView(mViewBinding.ivUserAvatar);
                loadImage.loadImage(this, imageParams);
            } else {
                mViewBinding.ivUserAvatar.setImageResource(R.drawable.default_avatar);
            }
            mViewBinding.tvUserId.setText("ID:" + user.getId());
            updateData(user, false);
        }
    }


    /**
     * 动态添加控件
     */
    private void dynamicAddWidget() {
        int height = DimensUtils.dp2px(this, 45f);
        int padding = DimensUtils.dp2px(this, 16f);
        String[] personInfoList = getResources().getString(R.string.person_info_item_list).split(",");
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
            tvName.setCompoundDrawablePadding(padding / 2);
            tvName.setTextSize(15f);
            tvName.setGravity(Gravity.CENTER_VERTICAL);
            tvName.setGravity(Gravity.RIGHT);
            tvName.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_666666, this));
            if (!StringUtils.isEmpty(infoList.get(i))) {
                tvName.setText(infoList.get(i));
            }
            llItemLayout.setTag(tvName);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(EventPostInfo eventPostInfo) {
        if (eventPostInfo != null && eventPostInfo.getStateType() == EventPostInfo.UPDATE_PERSON_INFO_SUCCESS) {
            User user = presenter.getUserInfo();
            updateData(user, true);
        }
    }

    /**
     * @param user
     */
    private void updateData(User user, boolean isUpdateView) {
        if (user != null) {
            infoList.put(0, user.getPhone());
            infoList.put(1, user.getUsername());
            infoList.put(2, user.getEmail());
            infoList.put(3, "");
            infoList.put(4, user.getSex());
            infoList.put(5, StringUtils.isEmpty(user.getBig_area()) ? "" : user.getBig_area() + (StringUtils.isEmpty(user.getProvince()) ? "" : user.getProvince()));
            if (isUpdateView) {
                int countChild = mViewBinding.llPersonInfoLayout.getChildCount();
                int index=0;
                for (int i = 0; i < countChild; i++) {
                    TextView tvName = (TextView) mViewBinding.llPersonInfoLayout.getChildAt(i).getTag();
                    if(tvName != null){
                        Logger.i("name=" + infoList.get(index) + ",tvName=" + tvName);
                        tvName.setText(infoList.get(index));
                        index ++;
                    }
                }
            }
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public int getLayoutId() {
        return R.layout.person_info;
    }

    @Override
    public void callbackUserInfo(RespondDO respondDO) {
        if (respondDO.isStatus()) {
            updateData((User) respondDO.getObject(), true);
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
                case 0://tel
                    RouterManager.goBindPhone();
                    break;
                case 1://nickanme
                    RouterManager.goNickname();
                    break;
                case 2://e-mail
                    RouterManager.goBindEmail();
                    break;
                case 3://date of birth
                    break;
                case 4://gender
                    break;
                case 5://region
                    RouterManager.goRegion();
                    break;
            }

        }
    }

}
