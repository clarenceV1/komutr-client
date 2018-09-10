package com.komutr.client.ui.message;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.cai.pullrefresh.PtrRecyclerView;
import com.cai.pullrefresh.RecycleViewDivider;
import com.example.clarence.utillibrary.DimensUtils;
import com.example.clarence.utillibrary.StreamUtils;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.been.Message;
import com.komutr.client.been.RespondDO;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.MessageBinding;

import java.util.List;

import javax.inject.Inject;

@Route(path = RouterManager.ROUTER_MESSAGE, name = "消息")
public class MessageActivity extends AppBaseActivity<MessageBinding> implements MessageView, View.OnClickListener {

    @Inject
    MessagePresenter presenter;


    PtrRecyclerView mPtrRecyclerView;
    MessageAdapter adapter;

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
        setBarTitle(getString(R.string.message_title));

        mPtrRecyclerView = (PtrRecyclerView) mViewBinding.ptyRecycle.getRecyclerView();
        mPtrRecyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, DimensUtils.dp2px(this, 1f), StreamUtils.getInstance().resourceToColor(R.color.transparent, this)));
        mPtrRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MessageAdapter(this, presenter);
        mPtrRecyclerView.setAdapter(adapter);
        onShowLoadDialog(getString(R.string.loading), this);
        presenter.requestMessage();
    }

    @Override
    public int getLayoutId() {
        return R.layout.message;
    }

    @Override
    public void callback(RespondDO<List<Message>> respondDO) {
        hiddenDialog();
        if (respondDO.isStatus()) {
            List<Message> data = respondDO.getObject();
            if (data != null && data.size() > 0) {//有数据
                adapter.setDatas(data);//下拉
            }
        } else {
            if (adapter.getCount() > 0) {
                ToastUtils.showShort(respondDO.getMsg());
            } else {
                onShowLoadError();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_dialog_reloading_text://刷新重新获取数据
                onShowLoadDialog(getString(R.string.loading), this);
                presenter.requestMessage();
                break;
        }
    }
}
