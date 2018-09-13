package com.komutr.client.ui.map;

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
import android.util.Log;
import android.widget.Toast;

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
import com.komutr.client.base.App;
import com.komutr.client.been.RoutesInfo;
import com.komutr.client.been.Station;
import com.komutr.client.been.StationDetail;
import com.komutr.client.been.User;
import com.komutr.client.event.EventPostInfo;
import com.komutr.client.event.PermissionEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class MapHelp implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private final String TAG = "BookFragment222";

    private GoogleMap mMap;
    private Context context;
    FusedLocationProviderClient mFusedLocationClient;
    LocationCallback mLocationCallback;
    LocationRequest mLocationRequest;
    MapCallback mapCallback;

    public MapHelp(Context context, SupportMapFragment mapFragment, MapCallback mapCallback) {
        this.context = context;
        this.mapCallback = mapCallback;
        mapFragment.getMapAsync(this);
        EventBus.getDefault().register(this);
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    /**
     * 时时定位
     */
    protected void createLocationRequest() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        SettingsClient client = LocationServices.getSettingsClient(context);
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setIndoorEnabled(false);
        mMap.setMinZoomPreference(0.0f);
        mMap.setMaxZoomPreference(20.0f);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);

        //先默认移动到菲律宾
        LatLng latLng = new LatLng(14.600402, 120.991269);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        loadMyLocation();
//        createLocationRequest();
        //地图定位
//        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startPoint, 10));
    }

    private void loadMyLocation() {
        LocationManager lm = (LocationManager) App.getAppContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        Location myLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (myLocation == null) {
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_HIGH);
            String provider = lm.getBestProvider(criteria, true);
            myLocation = lm.getLastKnownLocation(provider);
        }
        LatLng latLng = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        if (mapCallback != null) {
            mapCallback.getLocation(latLng);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        loadMyLocation();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(context, "Current location:\n" + location, Toast.LENGTH_LONG).show();
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

    /**
     * 添加站点标注
     */
    public void addStationMarker(LatLng point, int resourceId, String title) {
        mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(resourceId)).position(point).title(title));
    }

    /**
     * 画路线图
     *
     * @param routes
     */
    public void drawRoutes(RoutesInfo routes) {
        List<StationDetail> stations = routes.getAll_station();

        if (stations != null && stations.size() > 0) {
            PolylineOptions polylineOptions = new PolylineOptions()
                    .width(5)
                    .color(context.getResources().getColor(R.color.color_2196f3));
            LatLng point = null;
            BitmapDescriptor bitmapDescriptor = null;
            for (int i = 0; i < stations.size(); i++) {
                StationDetail detail = stations.get(i);
                point = new LatLng(detail.getLatitude(), detail.getLongitude());
                if (i == 0) {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.point_start);
                } else if (i == stations.size() - 1) {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.point_end);
                } else {
                    bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.point_center);
                }
                mMap.addMarker(new MarkerOptions().icon(bitmapDescriptor).position(point).title(detail.getStation_name()));
                polylineOptions.add(point);
            }
            Polyline polyline = mMap.addPolyline(polylineOptions);
            polyline.setJointType(JointType.ROUND);//24.63618 118.07404 25.98584 25.98584
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPermissionEvent(PermissionEvent event) {
        loadMyLocation();
    }
}
