package com.komutr.client.ui.map;

import com.google.android.gms.maps.model.LatLng;

public interface MapCallback {
    void getLocation(LatLng location);
}
