package com.komutr.client.common;


import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.cai.framework.base.GodBaseApplication;
import com.cai.framework.dagger.ActivityScope;
import com.example.clarence.utillibrary.NetWorkUtil;
import com.example.clarence.utillibrary.ToastUtils;
import com.komutr.client.R;
import com.komutr.client.been.RespondDO;

import java.util.Map;

import javax.inject.Inject;

import dagger.Lazy;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
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
                            if (!TextUtils.isEmpty(result)) {
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
