package com.komutr.client.ui.feedback;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.framework.logger.Logger;
import com.example.clarence.utillibrary.StringUtils;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.FeedbackBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_FEEDBACK, name = "意见反馈")
public class FeedbackActivity extends AppBaseActivity<FeedbackBinding> implements FeedbackView, TextView.OnEditorActionListener, TextWatcher, View.OnClickListener {
    @Inject
    FeedbackPresenter presenter;

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
        setBarTitle(getString(R.string.feedback_title));
        mViewBinding.etFeedBackContent.setOnEditorActionListener(this);
        mViewBinding.etFeedBackContent.addTextChangedListener(this);
        mViewBinding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.feedback;
    }

    @Override
    public void callback(RespondDO respondDO) {
        hiddenDialog();
        ToastUtils.showShort(respondDO.getMsg());
        if (respondDO.isStatus()) {
            finish();
        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

        if (actionId == EditorInfo.IME_ACTION_DONE) {
            Logger.i("完成操作执行");
            requestSubmit();
        }
        return false;
    }

    /**
     * 请求提交数据
     */
    private void requestSubmit() {

        if (!StringUtils.isEmpty(mViewBinding.etFeedBackContent.getText().toString())) {
            showDialog(getString(R.string.submitting), true);
            presenter.requestFeedBack(mViewBinding.etFeedBackContent.getText().toString());

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        int length = charSequence.toString().length();
        mViewBinding.tvContentLength.setText(length + "/500");
        mViewBinding.btnSubmit.setEnabled(length > 0);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSubmit://提交
                requestSubmit();
                break;
        }
    }
}
