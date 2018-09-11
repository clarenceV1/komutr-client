package com.komutr.client.common;


import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.dagger.ActivityScope;
import com.cai.framework.http.SocketFactory;
import com.example.clarence.utillibrary.NetWorkUtil;
import com.example.clarence.utillibrary.StringUtils;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.R;
import com.komutr.client.been.RespondDO;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import dagger.Lazy;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;


/**
 * 接口Rxjava生成
 */
@ActivityScope
public class AppRequestStore {
    @Inject
    Lazy<Retrofit> retrofit;
    @Inject
    Lazy<AppDataStore> dataStore;

    @Inject
    public AppRequestStore() {
    }

    public Single<RespondDO> commonRequest(Map<String, Object> queryMap) {

//        if(NetWorkUtil.isNetConnected(GodBaseApplication.getAppContext())){
//            ToastUtils.showShort(GodBaseApplication.getAppContext().getString(R.string.network_error));
//            return null;
//        }
        Single<RespondDO> respond = retrofit.get().create(ApiService.class)
                .commonRequest(queryMap)
                .map(new Function<ResponseBody, RespondDO>() {
                    @Override
                    public RespondDO apply(ResponseBody responseBody) {
                        RespondDO respondDO = new RespondDO();
                        try {
                            String result = responseBody.string();
                            Log.d("commonRequest", result);
                            if (!StringUtils.isEmpty(result)) {
                                respondDO = JSON.parseObject(result, RespondDO.class);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            respondDO.setMsg(e.getMessage());
                        }
                        return respondDO;
                    }
                })
                .subscribeOn(Schedulers.newThread());
        return respond;
    }

    public void uploadFile(Map<String, String> queryMap,File file) {



        MediaType type=MediaType.parse("application/octet-stream");//"text/xml;charset=utf-8"
        RequestBody fileBody=RequestBody.create(type,file);
        RequestBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.ALTERNATIVE)
                //一样的效果
                .addPart(Headers.of("Content-Disposition", "form-data; name=params"), getParams(queryMap))
                .addPart(Headers.of("Content-Disposition", "form-data; name=file; filename=plans.xml"), fileBody)
                //一样的效果
                /*.addFormDataPart("id",currentPlan.getPlanId()+"")
                .addFormDataPart("name",currentPlan.getName())
                .addFormDataPart("volume",currentPlan.getVolume())
                .addFormDataPart("type",currentPlan.getType()+"")
                .addFormDataPart("mode",currentPlan.getMode()+"")
                .addFormDataPart("params","plans.xml",fileBody)*/
                .build();
        Request request = new Request.Builder().url(Constant.OFFICIAL_BASE_URL+"sapi")
                .addHeader("User-Agent", "android")
                .header("Content-Type", "text/html; charset=utf-8;")
                .post(multipartBody)//传参数、文件或者混合，改一下就行请求体就行
                .build();
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("logInterceptor", message);
            }
        });
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        File cacheFile = new File(GodBaseApplication.getAppContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 10); //100Mb
        OkHttpClient.Builder  okHttpBuilder = new OkHttpClient.Builder()
                .readTimeout(30000, TimeUnit.MILLISECONDS)
                .connectTimeout(30000, TimeUnit.MILLISECONDS)
                .writeTimeout(30000, TimeUnit.MILLISECONDS)
                .addInterceptor(logInterceptor)
//                .addNetworkInterceptor(new StethoInterceptor())
                .cache(cache)
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(SocketFactory.createSSLSocketFactory());
        okHttpBuilder.build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Log.i("xxx","1、连接的消息"+response.message());
                if(response.isSuccessful()){
                    Log.i("xxx","2、连接成功获取的内容"+response.body().string());
                }
            }
        });
    }

    private FormBody getParams(Map<String, String> queryMap) {

        FormBody.Builder builder = null;
        for (String in : queryMap.keySet()) {
            //map.keySet()返回的是所有key的值
            if (builder == null) {
                builder = new FormBody.Builder().add(in, queryMap.get(queryMap));
            } else {
               builder.add(in, queryMap.get(queryMap));
            }
        }
        return builder.build();
    }


}
