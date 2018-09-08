package com.komutr.client.ui.status;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.example.clarence.utillibrary.StreamUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.StatusBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.PAY_STATUS, name = "搜索-搜索路线-支付确认-状态（包括支付状态、退票状态）")
public class StatusActivity extends AppBaseActivity<StatusBinding> implements StatusView, View.OnClickListener {
    @Inject
    StatusPresenter presenter;
    String orderId;

    boolean isPayStatus = true;//true为支付状态 false为退票状态

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

        mViewBinding.ivClosePage.setOnClickListener(this);
        mViewBinding.btnViewOrder.setOnClickListener(this);
        mViewBinding.btnComplete.setOnClickListener(this);

        int num = (int) ((Math.random() * 9 + 1) * 100000);
        if(isPayStatus){
            if ((num%2)==0) {//支付失败
                seFailStatus(getString(R.string.payment_fail));
            }else {//支付成功
//                mViewBinding.tvBalance.setText();//余额

            }
        }else{
            if ((num%2)==0) {//退票失败
                seFailStatus(getString(R.string.refund_fail));
            }else {//退票成功
                mViewBinding.tvStatus.setText(getString(R.string.refund_success));
                mViewBinding.btnViewOrder.setText(getString(R.string.view_details));
                mViewBinding.llRefundLayout.setVisibility(View.VISIBLE);
                mViewBinding.tvCenterName.setText(getString(R.string.deduct_expenses));
//                mViewBinding.tvBalance.setText();//违约金
//                mViewBinding.tvRefund.setText();//退款金额
            }
        }
    }

    /**
     * 设置失败状态
     * @param text
     */
    private void seFailStatus(String text) {

        mViewBinding.viewLine.setVisibility(View.GONE);
        mViewBinding.tvStatus.setCompoundDrawablesWithIntrinsicBounds(null, StreamUtils.getInstance().resourceToDrawable(R.drawable.status_fail, this), null, null);
        mViewBinding.tvStatus.setText(text);
        mViewBinding.tvStatus.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_e31d1d, this));
        mViewBinding.btnComplete.setBackgroundResource(R.drawable.main_solid_bg);
        mViewBinding.btnComplete.setTextColor(StreamUtils.getInstance().resourceToColor(R.color.color_ffffff, this));
        mViewBinding.btnViewOrder.setVisibility(View.GONE);
        mViewBinding.llCenterLayout.setVisibility(View.GONE);
    }


    @Override
    public int getLayoutId() {
        return R.layout.status;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivClosePage:
                finish();
                break;
            case R.id.btnViewOrder://查看订单
                 if(isPayStatus){//跳订单详情
                     RouterManager.goOrderDetails(orderId,false);
                 }else {//跳账单详情

                 }
                break;
            case R.id.btnComplete://完成
                RouterManager.goMain();
                break;
        }
    }
}
