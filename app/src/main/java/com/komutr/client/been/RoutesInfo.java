package com.komutr.client.been;

import java.util.List;

public class RoutesInfo {
//    {
//        "all_station": [{
//               "id": 30,
//                "latitude": "24.63618",
//                "longitude": "118.07404",
//                "route_id": 11,
//                "station_id": "8",
//                "station_name": "厦门站"
//    }, {
//        "id": 31,
//                "latitude": "25.98584",
//                "longitude": "119.39056",
//                "route_id": 11,
//                "station_id": "20",
//                "station_name": "福州北站"
//    }],
//        "now_bus": false
//    }

    private List<StationDetail> all_station;
    private boolean now_bus;

    public List<StationDetail> getAll_station() {
        return all_station;
    }

    public void setAll_station(List<StationDetail> all_station) {
        this.all_station = all_station;
    }

    public boolean isNow_bus() {
        return now_bus;
    }

    public void setNow_bus(boolean now_bus) {
        this.now_bus = now_bus;
    }
}
