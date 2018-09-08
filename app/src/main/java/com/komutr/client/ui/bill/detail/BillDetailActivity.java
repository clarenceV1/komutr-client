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
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.BillDetail;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.BillDetailBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_BILL_DETAIL, name = "扫码支付-账单详情/我的-退票账单详情/我的-我的钱包-充值账单详情")
public class BillDetailActivity extends AppBaseActivity<BillDetailBinding> implements BillDetailView, View.OnClickListener {
    @Inject
    BillDetailPresenter presenter;
    @Autowired(name = "BillId")
    String billId;
    BillDetail billDetail;

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
                        if (billDetail != null) {
                            presenter.deleteBill(billDetail.getBill_id());
                        }
                        dialog.dismiss();
                    }
                }).build().show();
    }

    @Override
    public void billDetailCallBack(RespondDO<BillDetail> respondDO) {
        if (respondDO.isStatus()) {
            billDetail = respondDO.getObject();
        }
    }

    @Override
    public void deleteBillCallBack(RespondDO<String> respondDO) {
        if (respondDO.isStatus()) {
            String deleteId = respondDO.getObject();
        }
    }
}
