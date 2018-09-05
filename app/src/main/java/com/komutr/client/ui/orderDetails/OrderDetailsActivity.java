package com.komutr.client.ui.orderDetails;

import android.content.DialogInterface;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.widget.dialog.GodDialog;
import com.example.clarence.utillibrary.StreamUtils;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.OrderDetailsBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ORDER_DETAILS, name = "搜索-搜索路线-支付确认-状态-订单详情")
public class OrderDetailsActivity extends AppBaseActivity<OrderDetailsBinding> implements OrderDetailsView, View.OnClickListener {
    @Inject
    OrderDetailsPresenter presenter;

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


        mViewBinding.tvRefundInstructions.setOnClickListener(this);
        mViewBinding.tvRefund.setOnClickListener(this);
        mViewBinding.ivBack.setOnClickListener(this);

        int useStatus = 3;//1:已使用 2:已退票 3:未使用
        if (useStatus == 1) {
            boolean isComment = true;//判断是否有评论过

            if (isComment) {//布局不需要更改

            } else {
                mViewBinding.viewCommentLine.setVisibility(View.GONE);
                mViewBinding.tvUserComment.setVisibility(View.GONE);
                mViewBinding.crbUserRatings.setVisibility(View.GONE);
                mViewBinding.tvDidNoScore.setVisibility(View.VISIBLE);
                mViewBinding.llRUserRatingLayout.setOnClickListener(this);
            }
        } else if (useStatus == 2) {
            mViewBinding.llRUserRatingLayout.setVisibility(View.GONE);
            mViewBinding.ivUseStatus.setVisibility(View.VISIBLE);
            mViewBinding.ivUseStatus.setImageDrawable(StreamUtils.getInstance().resourceToDrawable(R.drawable.used, this));
            mViewBinding.viewCommentLine.setVisibility(View.GONE);
            mViewBinding.tvUserComment.setVisibility(View.GONE);
        } else if (useStatus == 3) {
            mViewBinding.tvRefund.setText(getString(R.string.refund));
            mViewBinding.tvRefund.setCompoundDrawables(null, null, null, null);
            mViewBinding.ivUseStatus.setVisibility(View.VISIBLE);
            mViewBinding.ivUseStatus.setImageDrawable(StreamUtils.getInstance().resourceToDrawable(R.drawable.refunded, this));
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.order_details;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llRUserRatingLayout://等级评论
                break;

            case R.id.tvRefundInstructions://退款介绍
                showRefundInstructionsDialog("0.00");
                /*  */
                break;
            case R.id.tvRefund://退款、删除
                boolean isDeleteOrder = StringUtils.isEmpty(mViewBinding.tvRefund.getText().toString());//text为空是删除订单、不为空是退票
                showRefundDialog(isDeleteOrder, "5");
                break;
            case R.id.ivBack://
                finish();
                break;
        }
    }

    /**
     * 显示退款对话框
     */
    private void showRefundDialog(final boolean isDeleteOrder, String amount) {
        new GodDialog.Builder(this)
                .setTitle(getString(isDeleteOrder ? R.string.delete_order : R.string.ticket_confirmation))
                .setMessage(isDeleteOrder ? getString(R.string.delete_order_content) : getString(R.string.ticket_confirmation_content, amount))
                .setNegativeButton(getString(R.string.btn_cancle), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                                ToastUtils.showShort("点击取消了");
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                                ToastUtils.showShort("点击确定了");
                        dialog.dismiss();
                    }
                }).build().show();
    }

    /**
     * 显示退款介绍对话框
     *
     * @param amount 金额
     */
    private void showRefundInstructionsDialog(String amount) {

        new GodDialog.Builder(this)
                .setTitle(getString(R.string.refund_instructions))
                .setMessage(getString(R.string.refund_instructions_des, amount))
                .setOneButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        ToastUtils.showShort("点击单个按钮了");
                        dialog.dismiss();
                    }
                }).build().show();
    }


}
