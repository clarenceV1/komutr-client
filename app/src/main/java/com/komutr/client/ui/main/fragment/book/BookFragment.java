package com.komutr.client.ui.main.fragment.book;


import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.cai.framework.permission.RxPermissions;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.komutr.client.R;
import com.komutr.client.base.App;
import com.komutr.client.base.AppBaseFragment;
import com.komutr.client.been.RespondDO;
import com.komutr.client.been.Station;
import com.komutr.client.common.RouterManager;
import com.komutr.client.databinding.FragmentBookBinding;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookFragment extends AppBaseFragment<FragmentBookBinding> implements BookView, View.OnClickListener,
        GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, OnMapReadyCallback {

    @Inject
    BookPresenter presenter;
    double longitude;//精度
    double latitude;//纬度
    int offset;//开始行
    int limit;//数量
    int bigArea;//大区 ID
    int province;//大区下面的行政单位 ID
    boolean isChange = false;//是否切换

    Station begStation;//起点站
    Station endStation;//终点站
    private GoogleMap mMap;

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
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mViewBinding.ivChangeLocation.setOnClickListener(this);
        mViewBinding.ivSearchRoutes.setOnClickListener(this);
        mViewBinding.tvStartLocation.setOnClickListener(this);
        mViewBinding.tvEndLocation.setOnClickListener(this);

        //test
        limit = 1;
        offset = 0;
        longitude = 118.07404;
        latitude = 24.63618;

        presenter.requestNearby(longitude, latitude, offset, limit);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
//        enableMyLocation();

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setIndoorEnabled(false);
        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(20.0f);

        //添加坐标
        LatLng startPoint = new LatLng(24.483922, 118.180942);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.point_start)).position(startPoint).title("company"));
        LatLng endPoint = new LatLng(24.603243, 118.120948);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.point_end)).position(endPoint).title("home"));

        LatLng center = new LatLng(24.540565, 118.155498);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.point_center)).position(center).title("station_2"));
        LatLng center2 = new LatLng(24.492571, 118.138526);
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.point_center)).position(center2).title("station_1"));
        //画线
        PolylineOptions currPolylineOptions = new PolylineOptions()
                .width(5)
                .color(Color.parseColor("#2196f3"))
                .add(startPoint, center, center2, endPoint);
        Polyline polyline = mMap.addPolyline(currPolylineOptions);
        polyline.setJointType(JointType.ROUND);

        //地图定位
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 10));
    }

    @SuppressLint("CheckResult")
    private void enableMyLocation() {
        new RxPermissions(this).request(Manifest.permission.ACCESS_FINE_LOCATION).subscribe(new Consumer<Boolean>() {
            @SuppressLint("MissingPermission")
            @Override
            public void accept(Boolean granted) {
                if (mMap != null) {
                    mMap.setMyLocationEnabled(true);
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
        }else{
            mViewBinding.tvStartLocation.setText(begStation.getName());
        }
        if (endStation == null) {
            mViewBinding.tvEndLocation.setText("");
        }else{
            mViewBinding.tvEndLocation.setText(endStation.getName());
        }
    }

    @Override
    public void requestNearbyCallback(RespondDO<Station> respondDO) {
        if (respondDO.isStatus()) {
            Station nearby = respondDO.getObject();
            if (nearby != null) {
                if (isChange) {
                    endStation = nearby;
                    mViewBinding.tvEndLocation.setText(endStation.getName());
                } else {
                    begStation = nearby;
                    mViewBinding.tvStartLocation.setText(begStation.getName());
                }

            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }
}
