package com.komutr.client.common;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * 接口声明
 */
public interface ApiService {
    @GET("sapi")
    Single<ResponseBody> commonRequest(@QueryMap Map<String, Object> map);
}
