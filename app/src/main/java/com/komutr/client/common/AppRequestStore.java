package com.komutr.client.common;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.dagger.ActivityScope;
import com.example.clarence.utillibrary.StringUtils;
import com.komutr.client.been.RespondDO;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    public Single<RespondDO> uploadFile(Map<String, String> queryMap, String key, File file) {
        RequestBody requestFile = RequestBody.create(MultipartBody.FORM, file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(key, file.getName(), requestFile);

        Map<String, RequestBody> bodyMap = new HashMap<>();
        for (Map.Entry<String, String> entry : queryMap.entrySet()) {
            bodyMap.put(entry.getKey(), RequestBody.create(MultipartBody.FORM, entry.getValue()));
        }

        Single<RespondDO> respond = retrofit.get().create(ApiService.class)
                .uploadPic(bodyMap, body)
                .map(new Function<ResponseBody, RespondDO>() {
                    @Override
                    public RespondDO apply(ResponseBody responseBody) {
                        RespondDO respondDO = new RespondDO();
                        try {
                            String result = responseBody.string();
                            Log.d("uploadPic", result);
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
}
