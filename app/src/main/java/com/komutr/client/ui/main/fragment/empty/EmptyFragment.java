package com.komutr.client.ui.main.fragment.empty;


import android.support.v4.app.Fragment;
import android.view.View;

import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseFragment;
import com.komutr.client.databinding.FragmentEmptyBinding;
import com.komutr.client.ui.main.fragment.book.BookPresenter;

import java.util.List;

import javax.inject.Inject;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EmptyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmptyFragment extends AppBaseFragment<FragmentEmptyBinding> {

    @Inject
    BookPresenter presenter;

    public EmptyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EmptyFragment newInstance() {
        EmptyFragment fragment = new EmptyFragment();
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
        return R.layout.fragment_empty;
    }

    @Override
    public void initView(View view) {

    }

}
