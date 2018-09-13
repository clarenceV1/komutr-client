package com.komutr.client.ui.main.fragment.book;


import android.support.v4.app.Fragment;
import android.view.View;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseFragment;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Station;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.FragmentBookBinding;
import com.komutr.client.ui.map.MapCallback;
import com.komutr.client.ui.map.MapHelp;

import java.util.List;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends AppBaseFragment<FragmentBookBinding> implements BookView, View.OnClickListener {

    @Inject
    BookPresenter presenter;
    int offset = 0;//开始行
    int limit = 100;//数量
    int bigArea;//大区 ID
    int province;//大区下面的行政单位 ID
    boolean isChange = false;//是否切换

    Station begStation;//起点站
    Station endStation;//终点站

    MapHelp mapHelp;
    LatLng mLocation;

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
    public void onDestroy() {
        super.onDestroy();
        if (mapHelp != null) {
            mapHelp.onDestroy();
        }
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
        initMap();

        mViewBinding.ivChangeLocation.setOnClickListener(this);
        mViewBinding.ivSearchRoutes.setOnClickListener(this);
        mViewBinding.tvStartLocation.setOnClickListener(this);
        mViewBinding.tvEndLocation.setOnClickListener(this);

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapHelp = new MapHelp(getContext(), mapFragment, new MapCallback() {
            @Override
            public void getLocation(LatLng location) {
                mLocation = location;
                if (location != null) {
//                    mLocation = new LatLng(24.63618,118.07404f);//todo test data
                    presenter.requestNearby(mLocation.latitude, mLocation.longitude, offset, limit);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivChangeLocation://位置切换
                isChange = !isChange;
                switchStation();
                break;
            case R.id.ivSearchRoutes://搜索路线
                RouterManager.goSearchRoutes(8, 20);//todo  test
//                if (begStation != null && endStation != null) {
//                    RouterManager.goSearchRoutes(begStation.getId(), endStation.getId());
//                } else {
//                    ToastUtils.showShort("起点/终点不能为空");//todo  带完善
//                }
                break;
            case R.id.tvStartLocation://起点位置
                RouterManager.goPosition(true, bigArea, province);
                break;
            case R.id.tvEndLocation://终点位置
                RouterManager.goPosition(false, bigArea, province);
                break;
        }
    }

    /**
     * 切换站点
     */
    private void switchStation() {
        Station temp = begStation;
        begStation = endStation;
        endStation = temp;
        if (begStation == null) {
            mViewBinding.tvStartLocation.setText("");
        } else {
            mViewBinding.tvStartLocation.setText(begStation.getName());
        }
        if (endStation == null) {
            mViewBinding.tvEndLocation.setText("");
        } else {
            mViewBinding.tvEndLocation.setText(endStation.getName());
        }
    }

    @Override
    public void requestNearbyCallback(RespondDO<List<Station>> respondDO) {
        if (respondDO.isStatus() && respondDO.getObject() != null) {
            List<Station> nears = respondDO.getObject();
            if (mLocation != null) {
                presenter.getNearestDistance(mLocation, nears);
            }
            if (mapHelp != null) {
                for (Station nearby : nears) {
                    LatLng point = new LatLng(nearby.getLatitude(), nearby.getLongitude());
                    mapHelp.addStationMarker(point, R.drawable.station_point, nearby.getName());
                }
            }
        }
    }

    @Override
    public void nearestDistanceCallback(Station stations) {
        if (stations != null) {
            if (isChange) {
                endStation = stations;
                mViewBinding.tvEndLocation.setText(endStation.getName());
            } else {
                begStation = stations;
                mViewBinding.tvStartLocation.setText(begStation.getName());
            }
        }
    }
}
