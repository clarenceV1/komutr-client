package com.komutr.client.ui.main.fragment.book;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.cai.framework.permission.RxPermissions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.tasks.OnSuccessListener;

import io.reactivex.functions.Consumer;

public class MapHelp implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener{

    private final String TAG = "BookFragment";

    private GoogleMap mMap;
    private Fragment fragment;

    private FusedLocationProviderClient mFusedLocationClient;

    public void init(Fragment fragment, SupportMapFragment mapFragment) {
        this.fragment = fragment;
        mapFragment.getMapAsync(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(fragment.getContext());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setIndoorEnabled(false);
        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(20.0f);

//        //添加坐标
//        LatLng startPoint = new LatLng(24.483922, 118.180942);
//        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.point_start)).position(startPoint).title("company"));
//        LatLng endPoint = new LatLng(24.603243, 118.120948);
//        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.point_end)).position(endPoint).title("home"));
//
//        LatLng center = new LatLng(24.540565, 118.155498);
//        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.point_center)).position(center).title("station_2"));
//        LatLng center2 = new LatLng(24.492571, 118.138526);
//        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.point_center)).position(center2).title("station_1"));
//        //画线
//        PolylineOptions currPolylineOptions = new PolylineOptions()
//                .width(5)
//                .color(Color.parseColor("#2196f3"))
//                .add(startPoint, center, center2, endPoint);
//        Polyline polyline = mMap.addPolyline(currPolylineOptions);
//        polyline.setJointType(JointType.ROUND);

        //地图定位
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 10));
    }


    @SuppressLint("CheckResult")
    private void enableMyLocation() {
        new RxPermissions(fragment).request(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean granted) {
                if (mMap != null) {
                    if (ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mMap.setMyLocationEnabled(true);
                    mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {

                                    }
                                }
                            });
//                    Location mLocation = getMyLocation();
//                    if (mLocation != null) {
//                        LatLng latLng = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
//                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//                        Log.d(TAG, mLocation.toString());
//                    } else {
//                        Log.d(TAG, mLocation.toString());
//                    }
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    private Location getMyLocation() {
        LocationManager lm = (LocationManager) fragment.getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
            String provider = lm.getBestProvider(criteria, true);
            myLocation = lm.getLastKnownLocation(provider);
        }
        return myLocation;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(fragment.getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(fragment.getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
