package com.komutr.client.ui.routes;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.cai.framework.base.GodBasePresenter;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseActivity;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.SearchRoutesBinding;

import java.util.List;

import javax.inject.Inject;



@Route(path = RouterManager.SEARCH_ROUTES, name = "搜索-搜索路线")
public class SearchRoutesActivity extends AppBaseActivity<SearchRoutesBinding> implements SearchRoutesView {

    @Inject
    SearchRoutesPresenter presenter;

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



    }

    @Override
    public int getLayoutId() {
        return R.layout.search_routes;
    }

}
