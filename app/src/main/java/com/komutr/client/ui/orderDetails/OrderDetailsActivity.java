package com.komutr.client.ui.orderDetails;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.imageload.GlideCircleTransform;
import com.cai.framework.imageload.ILoadImage;
import com.cai.framework.imageload.ILoadImageParams;
import com.cai.framework.imageload.ImageForGlideParams;
import com.cai.framework.widget.dialog.GodDialog;
import com.example.clarence.utillibrary.CommonUtils;
import com.example.clarence.utillibrary.DateUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.BuySellTicketDetails;
import com.komutr.client.been.Chauffeur;
import com.komutr.client.been.OrderDetail;
import com.komutr.client.been.OrderDetailDepartureTime;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.Constant;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.OrderDetailsBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ORDER_DETAILS, name = "搜索-搜索路线-支付确认-状态-订单详情")
public class OrderDetailsActivity extends AppBaseActivity<OrderDetailsBinding> implements OrderDetailsView, View.OnClickListener {

    @Inject
    OrderDetailsPresenter presenter;
    @Inject
    ILoadImage iLoadImage;
    @Autowired(name = "orderId")
    String orderId;
    @Autowired(name = "hasComment")
    boolean hasComment;// 是否带评论

    OrderDetail orderDetail;


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

        CommonUtils.setBackground(mViewBinding.tvRefundInstructions, CommonUtils.selectorStateColor(this, R.color.white, R.color.color_f1f1f4));
        mViewBinding.tvRefundInstructions.setOnClickListener(this);
        mViewBinding.tvRefund.setOnClickListener(this);
        mViewBinding.ivBack.setOnClickListener(this);

        int useStatus = 2;//1:未使用 2:已使用 3:已退票
        if (useStatus == 1) {
            hiddenCommentLayout();
        } else if (useStatus == 2) {

            mViewBinding.ivUseStatus.setVisibility(View.VISIBLE);
            mViewBinding.ivUseStatus.setImageDrawable(StreamUtils.getInstance().resourceToDrawable(R.drawable.used, this));
            boolean isComment = false;//判断是否有评论过
            if (isComment) {//布局不需要更改
            } else {
                hiddenCommentLayout();
                CommonUtils.setBackground(mViewBinding.llRUserRatingLayout, CommonUtils.selectorStateColor(this, R.color.white, R.color.color_f1f1f4));
                mViewBinding.llRUserRatingLayout.setOnClickListener(this);
            }
        } else if (useStatus == 3) {
            hiddenCommentLayout();
            mViewBinding.tvRefund.setText(getString(R.string.refund));
            mViewBinding.tvRefund.setCompoundDrawables(null, null, null, null);
            mViewBinding.ivUseStatus.setVisibility(View.VISIBLE);
            mViewBinding.ivUseStatus.setImageDrawable(StreamUtils.getInstance().resourceToDrawable(R.drawable.refunded, this));
        }
        onShowLoadDialog(getString(R.string.please_wait), this);
        presenter.getOrderDetail(orderId, true);
        presenter.requestImportant();
    }

    /**
     * 隐藏评论试图
     */
    private void hiddenCommentLayout() {
        mViewBinding.viewCommentLine.setVisibility(View.GONE);
        mViewBinding.tvUserComment.setVisibility(View.GONE);
        mViewBinding.crbUserRatings.setVisibility(View.GONE);
        mViewBinding.tvDidNoScore.setVisibility(View.VISIBLE);

    }

    @Override
    public int getLayoutId() {
        return R.layout.order_details;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llRUserRatingLayout://等级评论
                RouterManager.goUserRatings((Chauffeur) view.getTag());
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
            case R.id.tv_dialog_reloading_text:
                presenter.getOrderDetail(orderId, true);
                presenter.requestImportant();
                break;
        }
    }

    /**
     * 显示退款、删除订单对话框
     */
    private void showRefundDialog(final boolean isDeleteOrder, String amount) {
        new GodDialog.Builder(this)
                .setTitle(getString(isDeleteOrder ? R.string.delete_order : R.string.ticket_confirmation))
                .setMessage(isDeleteOrder ? getString(R.string.delete_order_content) : getString(R.string.ticket_confirmation_content, amount))
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                                ToastUtils.showShort("点击取消了");
                        dialog.dismiss();
                    }
                })
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                                ToastUtils.showShort("点击确定了");
                        presenter.refundTicket(orderId, 1);
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


    @Override
    public void orderDetailCallback(RespondDO<OrderDetail> respondDO) {
        hiddenDialog();
        if (respondDO.isStatus()) {
            orderDetail = respondDO.getObject();
            if (orderDetail != null) {
                mViewBinding.tvTicketPriceNum.setText(getString(R.string.ticket_price_price, orderDetail.getAmount()));
                mViewBinding.tvOrderId.setText(orderDetail.getOrder_number());
                mViewBinding.tvBusNumber.setText(orderDetail.getRoute_id());
                mViewBinding.tvStartLocation.setText(orderDetail.getStation() != null ? orderDetail.getStation().getBeg_station() : "");
                mViewBinding.tvEndLocation.setText(orderDetail.getStation() != null ? orderDetail.getStation().getEnd_station() : "");
                OrderDetailDepartureTime detailDepartureTime = orderDetail.getDeparture_time();
                if (detailDepartureTime != null) {
                    int hour = Integer.valueOf(detailDepartureTime.getHour());
                    String[] ams = getString(R.string.am_pm).split(",");
                    mViewBinding.tvTime.setText(detailDepartureTime.getHour() + ":" + detailDepartureTime.getMinute() + (hour > 12 ? ams[1] : ams[0]));
                }
                Chauffeur chauffeur = orderDetail.getChauffeur();
                if (chauffeur != null) {
                    mViewBinding.tvLicensePlateNo.setText(chauffeur.getLicence_plate());
                    if (!StringUtils.isEmpty(chauffeur.getAvatar())) {
//                    String icon = Constant.OFFICIAL_BASE_URL.substring(0, Constant.OFFICIAL_BASE_URL.length() - 1) + chauffeur.getAvatar_thum();
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
                        mViewBinding.llRUserRatingLayout.setTag(chauffeur);
                    }
                }


            }
        }

    }

    @Override
    public void importantCallBack(RespondDO<BuySellTicketDetails> respondDO) {
        if (respondDO.isStatus() && respondDO.getObject() != null) {
            BuySellTicketDetails buySellTicketDetails = respondDO.getObject();
            BuySellTicketDetails.Refund refund = buySellTicketDetails.getRefund();
            if (refund != null) {
                mViewBinding.tvImportantNotes.setText(refund.getContent());
                mViewBinding.tvImportantTitle.setText(refund.getTitle());
            }
        }
    }
}
