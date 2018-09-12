package com.komutr.client.ui.userRatings;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.imageload.GlideCircleTransform;
import com.cai.framework.imageload.ILoadImage;
import com.cai.framework.imageload.ILoadImageParams;
import com.cai.framework.imageload.ImageForGlideParams;
import com.cai.framework.logger.Logger;
import com.cai.framework.logger.Printer;
import com.cai.framework.widget.CustomRatingBar;
import com.example.clarence.utillibrary.StringUtils;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.Chauffeur;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.UserRatingsBinding;
import com.komutr.client.databinding.WalletBinding;
import com.komutr.client.event.EventPostInfo;
import com.komutr.client.ui.wallet.WalletPresenter;
import com.komutr.client.ui.wallet.WalletView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.USER_RATINGS, name = "搜索-搜索路线-支付确认-状态-订单详情-用户评分")
public class UserRatingsActivity extends AppBaseActivity<UserRatingsBinding> implements UserRatingsView, TextWatcher,View.OnClickListener {

    @Autowired(name = "chauffeur")
    Chauffeur chauffeur;//序列号的对象没办法自动解析，需要getArouterSerializableData

    @Autowired(name = "orderId")
    String orderId;

    @Autowired(name = "shiftId")
    String shiftId;
    @Inject
    UserRatingsPresenter presenter;
    @Inject
    ILoadImage iLoadImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ARouter.getInstance().inject(this);
        super.onCreate(savedInstanceState);
    }


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
        setBarTitle(getString(R.string.user_ratings));
        chauffeur = getArouterSerializableData("chauffeur");
        mViewBinding.etUserCommentContent.addTextChangedListener(this);
        mViewBinding.btnSubmit.setOnClickListener(this);
        if (chauffeur != null) {

            if (!StringUtils.isEmpty(chauffeur.getAvatar())) {
//              String icon = Constant.OFFICIAL_BASE_URL.substring(0, Constant.OFFICIAL_BASE_URL.length() - 1) + chauffeur.getAvatar_thum();
                ILoadImageParams imageParams = new ImageForGlideParams.Builder()
                        .url(chauffeur.getAvatar())
                        .error(R.drawable.default_avatar)
                        .placeholder(R.drawable.default_avatar)
                        .transformation(new GlideCircleTransform(this))
                        .build();
                imageParams.setImageView(mViewBinding.ivDriverAvatar);
                iLoadImage.loadImage(this, imageParams);
                mViewBinding.tvDriverName.setText(chauffeur.getUsername());
                mViewBinding.tvDriverPhoneNum.setText(chauffeur.getPhone());
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.user_ratings;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int length = charSequence.toString().length();
        mViewBinding.tvContentLength.setText(length + "/60");
        mViewBinding.btnSubmit.setEnabled(length > 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void userRatingsCallBack(RespondDO respondDO) {
        hiddenDialog();
        ToastUtils.showShort(respondDO.getMsg());
        if (respondDO.isStatus()) {
            EventBus.getDefault().post(new EventPostInfo(EventPostInfo.ORDER_DELETE_SUCCESS));
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        showDialog(getString(R.string.please_wait),true);
        presenter.userRatings(orderId,shiftId,chauffeur.getId(),StringUtils.getString(mViewBinding.etUserCommentContent),mViewBinding.crbUserRatings.getStarStep());
    }
}
