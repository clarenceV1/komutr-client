package com.komutr.client.ui.bill.detail;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.widget.dialog.GodDialog;
import com.example.clarence.utillibrary.StreamUtils;
import com.example.clarence.utillibrary.StringUtils;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.BillDetail;
import com.komutr.client.been.BillDetailItem;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.BillDetailBinding;
import com.komutr.client.event.EventPostInfo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_BILL_DETAIL, name = "扫码支付-账单详情/我的-退票账单详情/我的-我的钱包-充值账单详情")
public class BillDetailActivity extends AppBaseActivity<BillDetailBinding> implements BillDetailView, View.OnClickListener {
    @Inject
    BillDetailPresenter presenter;
    @Autowired(name = "BillId")
    String billId;

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

        setBarTitle(getString(R.string.bill_details));
        if (titleBarView != null) {
            titleBarView.setRightImage(StreamUtils.getInstance().resourceToDrawable(R.drawable.delete, this));
            titleBarView.setRightClickListener(this);
        }
        onShowLoadDialog(getString(R.string.please_wait),this);
        presenter.requestBillDetail(billId);


    }

    @Override
    public int getLayoutId() {
        return R.layout.bill_detail;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case com.cai.framework.R.id.tvRight://删除订单
                showRefundDialog();
                break;
            case R.id.tv_dialog_reloading_text://刷新
                presenter.requestBillDetail(billId);
                break;
        }
    }


    /**
     * 显示删除订单对话框
     */
    private void showRefundDialog() {
        new GodDialog.Builder(this)
                .setTitle(getString(R.string.delete_order))
                .setMessage(getString(R.string.delete_order_content))
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
                        dialog.dismiss();
                        if (!StringUtils.isEmpty(billId)) {
                            showDialog(getString(R.string.deleting),true);
                            presenter.deleteBill(billId);
                        }

                    }
                }).build().show();
    }

    @Override
    public void billDetailCallBack(RespondDO<BillDetail> respondDO) {
        hiddenDialog();
        if (respondDO.isStatus()) {
            BillDetail billDetail = respondDO.getObject();
            mViewBinding.tvOrderNum.setText(billDetail.getOrder_number());
            mViewBinding.tvStatus.setText(billDetail.getStatus());
            mViewBinding.tvTime.setText(billDetail.getCreate_at());
            mViewBinding.tvBillAmount.setText("+ ₱ " + (StringUtils.isEmpty(billDetail.getAmount()) ? getString(R.string.default_amount) : billDetail.getAmount()));
            BillDetailItem billDetailItem = billDetail.getItem();
            if(billDetailItem != null){
                mViewBinding.tvItems.setText(billDetailItem.getName());
            }
        }else {
            ToastUtils.showShort(respondDO.getMsg());
        }
    }

    @Override
    public void deleteBillCallBack(RespondDO<String> respondDO) {
        hiddenDialog();
        ToastUtils.showShort(respondDO.getMsg());
        if (respondDO.isStatus()) {
            EventBus.getDefault().post(new EventPostInfo(EventPostInfo.BILL_DELETE_SUCCESS));
            String deleteId = respondDO.getObject();
            finish();
        }

    }
}
