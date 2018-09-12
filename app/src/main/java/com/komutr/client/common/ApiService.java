package com.komutr.client.common;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.QueryMap;

/**
 * 接口声明
 */
public interface ApiService {


    @FormUrlEncoded
    @POST("sapi")
    Single<ResponseBody> commonRequest(@FieldMap Map<String, Object> map);


    @Multipart
    @POST("sapi")
    Single<ResponseBody> uploadPic(@PartMap Map<String, RequestBody> params, @Part MultipartBody.Part file);
}
