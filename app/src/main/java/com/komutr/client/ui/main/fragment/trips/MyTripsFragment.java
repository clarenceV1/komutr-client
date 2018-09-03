package com.komutr.client.ui.main.fragment.trips;


import android.support.v4.app.Fragment;
import android.view.View;

import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseFragment;
import com.komutr.client.databinding.FragmentEmptyBinding;
import com.komutr.client.databinding.FragmentMyTripsBinding;
import com.komutr.client.ui.main.fragment.book.BookPresenter;

import java.util.List;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyTripsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyTripsFragment extends AppBaseFragment<FragmentMyTripsBinding> {

    @Inject
    BookPresenter presenter;

    public MyTripsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyTripsFragment newInstance() {
        MyTripsFragment fragment = new MyTripsFragment();
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
        return R.layout.fragment_my_trips;
    }

    @Override
    public void initView(View view) {

    }

}
