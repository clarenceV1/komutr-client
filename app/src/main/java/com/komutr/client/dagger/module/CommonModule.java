package com.komutr.client.dagger.module;


import com.komutr.client.base.HeaderInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;

@Module
public class CommonModule {

    @Singleton
    @Provides
    public Interceptor provideHeadInterceptor() {
        return HeaderInterceptor.getInstance();
    }
}
