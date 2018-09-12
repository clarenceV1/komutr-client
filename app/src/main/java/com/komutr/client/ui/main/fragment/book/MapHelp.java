package com.komutr.client.ui.main.fragment.book;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.Toast;

import com.cai.framework.logger.Logger;
import com.cai.framework.permission.RxPermissions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.komutr.client.R;
import com.komutr.client.been.RouterStation;
import com.komutr.client.been.RoutesInfo;
import com.komutr.client.been.SearchRoutes;
import com.komutr.client.been.Station;
import com.komutr.client.been.StationDetail;

import java.util.List;

import io.reactivex.functions.Consumer;


public class MapHelp implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final String TAG = "BookFragment222";

    private GoogleMap mMap;
    private Fragment fragment;
    FusedLocationProviderClient mFusedLocationClient;
    LocationCallback mLocationCallback;
    LocationRequest mLocationRequest;

    @SuppressLint("MissingPermission")
    public void init(Fragment fragment, SupportMapFragment mapFragment) {
        this.fragment = fragment;
        mapFragment.getMapAsync(this);
    }

    @SuppressLint("MissingPermission")
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(fragment.getContext());
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @SuppressLint("MissingPermission")
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                Log.d(TAG, locationSettingsResponse.toString());
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    Log.d(TAG, e.toString());
                }
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(fragment.getContext());
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                Location location = locationResult.getLastLocation();
                Log.d(TAG, location.getLatitude() + ":" + location.getLongitude());
//                if (location != null) {
//                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
//                }
            }
        };
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setIndoorEnabled(false);
        mMap.setMinZoomPreference(0.0f);
        mMap.setMaxZoomPreference(20.0f);


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
                    reloadMyLocation();
                    createLocationRequest();
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }

    private void reloadMyLocation() {
        LocationManager lm = (LocationManager) fragment.getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_HIGH);
            String provider = lm.getBestProvider(criteria, true);
            myLocation = lm.getLastKnownLocation(provider);
        }
        LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        reloadMyLocation();
        Toast.makeText(fragment.getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
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

    @SuppressLint("MissingPermission")
    public void onResume() {

    }

    public void onPause() {
    }

    public void drawRoutes(RoutesInfo routes) {
        List<StationDetail> stations = routes.getAll_station();

        if (stations != null && stations.size() > 0) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .width(5)
                    .color(fragment.getResources().getColor(R.color.color_2196f3));
            LatLng point = null;
            BitmapDescriptor bitmapDescriptor = null;
            for (int i = 0; i < stations.size(); i++) {
                StationDetail detail = stations.get(i);
                point = new LatLng(detail.getLatitude(), detail.getLongitude());
                if (i == 0) {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.point_start);
                } else if (i == stations.size() - 1) {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.point_end);
                }else{
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.point_center);
                }
                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptor).position(point).title(detail.getStation_name()));
                polylineOptions.add(point);
            }
            Polyline polyline = mMap.addPolyline(polylineOptions);
            polyline.setJointType(JointType.ROUND);//24.63618 118.07404 25.98584 25.98584
        }
    }
}
