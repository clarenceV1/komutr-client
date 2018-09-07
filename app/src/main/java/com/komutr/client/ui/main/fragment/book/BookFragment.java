package com.komutr.client.ui.main.fragment.book;


import android.support.v4.app.Fragment;
import android.view.View;

import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseFragment;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.FragmentBookBinding;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends AppBaseFragment<FragmentBookBinding> implements View.OnClickListener{

    @Inject
    BookPresenter presenter;

    public BookFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookFragment newInstance() {
        BookFragment fragment = new BookFragment();
        return fragment;
    }


    @Override
    public void initDagger() {
        App.getAppComponent().inject(this);
    }

    @Override
    public void addPresenters(List observerList) {
        observerList.add(presenter);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_book;
    }

    @Override
    public void initView(View view) {

        mViewBinding.ivChangeLocation.setOnClickListener(this);
        mViewBinding.ivSearchRoutes.setOnClickListener(this);
        mViewBinding.tvStartLocation.setOnClickListener(this);
        mViewBinding.tvEndLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivChangeLocation://位置切换
                break;
            case R.id.ivSearchRoutes://搜索路线
                RouterManager.goSearchRoutes("ss","bb");
                break;

            case R.id.tvStartLocation://起点位置
                RouterManager.goPosition(true);
                break;

            case R.id.tvEndLocation://终点位置
                RouterManager.goPosition(false);
                break;

        }
    }
}
