package com.komutr.client.base;

import com.cai.framework.utils.LanguageLocalUtil;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private static class Holder {
        private static HeaderInterceptor interceptor = new HeaderInterceptor();
    }

    private HeaderInterceptor() {
    }

    public static HeaderInterceptor getInstance() {
        return Holder.interceptor;
    }

    public Headers getHeader() {
        Headers.Builder builder = new Headers.Builder();
        builder.add("platform", "2");
        builder.add("lang", LanguageLocalUtil.getSystemLanguage().toLowerCase());
//        if (provider != null) {
//            String token = provider.getTokent();
//            if (!TextUtils.isEmpty(token)) {
//                builder.add("auth", token);
//            }
//            try {
//                Context context = provider.geContext();
//                if (context != null) {
//                    builder.add("version", PackageUtils.getVersionName(context));
//                    builder.add("openudid", UniqueIdUtils.getDeviceInfo(context, UniqueIdUtils.DEVICES_INFO.ANDROID_ID));
//                    builder.add("imei", UniqueIdUtils.getDeviceInfo(context, UniqueIdUtils.DEVICES_INFO.IMEI));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
        return builder.build();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Headers headers = getHeader();
        if (headers != null) {
            try {
                Request request = chain.request().newBuilder()
                        .headers(headers)
                        .build();
                return chain.proceed(request);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return chain.proceed(chain.request());
    }
}
