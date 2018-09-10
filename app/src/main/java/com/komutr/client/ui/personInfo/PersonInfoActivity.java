package com.komutr.client.ui.personInfo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.imageload.GlideCircleTransform;
import com.cai.framework.imageload.ILoadImage;
import com.cai.framework.imageload.ILoadImageParams;
import com.cai.framework.imageload.ImageForGlideParams;
import com.cai.framework.logger.Logger;
import com.example.clarence.utillibrary.CommonUtils;
import com.example.clarence.utillibrary.DateUtils;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
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
                int index = 0;
                for (int i = 0; i < countChild; i++) {
                    TextView tvName = (TextView) mViewBinding.llPersonInfoLayout.getChildAt(i).getTag();
                    if (tvName != null) {
                        tvName.setText(infoList.get(index));
                        index++;
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
                    TimePickerView tag = (view.getTag(R.id.tag_first) == null) ? getDateDialog(view) : (TimePickerView) view.getTag(R.id.tag_first);
                    tag.show();
                    break;
                case 4://gender
                    OptionsPickerView dialog = (view.getTag(R.id.tag_first) == null) ? getSexDialog(view) : (OptionsPickerView) view.getTag(R.id.tag_first);
                    dialog.show();
                    break;
                case 5://region
                    RouterManager.goRegion();
                    break;
            }

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片路径
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            c.close();
        }
    }


    /**
     * 显示生日的选择器
     * @param view
     * @return
     */
    private TimePickerView getDateDialog(final View view) {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        //正确设置方式 原因：注意事项有说明
        startDate.set(1940, 1, 1);
        String[] currentTime = DateUtils.formatDate(System.currentTimeMillis()).split("-");
        endDate.set(Integer.valueOf(currentTime[0]), Integer.valueOf(currentTime[1]), Integer.valueOf(currentTime[2]));
//        String[] years = getResources().getString(R.string.year_mouth_day).split(",");

        TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                ((TextView) view.getTag()).setText(DateUtils.getStringDateShort(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText(getString(R.string.btn_cancle))//取消按钮文字
                .setSubmitText(getString(R.string.sure))//确认按钮文字
                .setContentTextSize(22)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(StreamUtils.getInstance().resourceToColor(R.color.color_000000, this))//标题文字颜色
                .setSubmitColor(StreamUtils.getInstance().resourceToColor(R.color.color_333333, this))//确定按钮文字颜色
                .setCancelColor(StreamUtils.getInstance().resourceToColor(R.color.color_main, this))//取消按钮文字颜色
                .setTitleBgColor(StreamUtils.getInstance().resourceToColor(R.color.color_ffffff, this))//标题背景颜色 Night mode
                .setBgColor(StreamUtils.getInstance().resourceToColor(R.color.color_f1f1f4, this))//滚轮背景颜色 Night mode
                .setDividerColor(StreamUtils.getInstance().resourceToColor(R.color.color_side_line, this))
                .setTextColorCenter(StreamUtils.getInstance().resourceToColor(R.color.color_333333, this)) //设置选中项文字颜色
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("", "", "", "时", "分", "秒")//默认设为年月日时分秒
                .isCenterLabel(true) //是否只显示中间选中项的 label 文字，false 则每项 item 全部都带有 label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        view.setTag(R.id.tag_first, pvTime);
        return pvTime;
    }


    /**
     * @param view
     * @return
     */
    private OptionsPickerView getSexDialog(final View view) {

        final List<String> options1Items = new ArrayList();
        String[] sexList = getResources().getString(R.string.sex_list).split(",");
        options1Items.addAll(Arrays.asList(sexList));
       final OptionsPickerView optionsPickerView = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                ((TextView) view.getTag()).setText(options1Items.get(options1));
            }
        })
                .setCancelText(getString(R.string.btn_cancle))//取消按钮文字
                .setSubmitText(getString(R.string.sure))//确认按钮文字
                .setContentTextSize(22)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .setTitleColor(StreamUtils.getInstance().resourceToColor(R.color.color_000000, this))//标题文字颜色
                .setSubmitColor(StreamUtils.getInstance().resourceToColor(R.color.color_333333, this))//确定按钮文字颜色
                .setCancelColor(StreamUtils.getInstance().resourceToColor(R.color.color_main, this))//取消按钮文字颜色
                .setTitleBgColor(StreamUtils.getInstance().resourceToColor(R.color.color_ffffff, this))//标题背景颜色 Night mode
                .setBgColor(StreamUtils.getInstance().resourceToColor(R.color.color_f1f1f4, this))//滚轮背景颜色 Night mode
                .setDividerColor(StreamUtils.getInstance().resourceToColor(R.color.color_side_line, this))
                .setTextColorCenter(StreamUtils.getInstance().resourceToColor(R.color.color_333333, this)) //设置选中项文字颜色
                .isCenterLabel(true) //是否只显示中间选中项的 label 文字，false 则每项 item 全部都带有 label。
                .isDialog(false)//是否显示为对话框样式
                /*.setLayoutRes(R.layout.pop_custom_layout, new CustomListener() {
                    @Override
                    public void customLayout(View v) {

                        //自定义布局中的控件初始化及事件处理
                        final TextView tvSubmit =  v.findViewById(R.id.tvSure);
                        final TextView tvCancel =  v.findViewById(R.id.tvCancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                optionsPickerView.returnData();
                                optionsPickerView.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                optionsPickerView.dismiss();
                            }
                        });
                    }
                })*/
                .build();
        optionsPickerView.setPicker(options1Items);
        view.setTag(R.id.tag_first, optionsPickerView);
        return optionsPickerView;
    }



}
