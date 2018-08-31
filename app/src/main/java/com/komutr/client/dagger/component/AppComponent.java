package com.komutr.client.dagger.component;


import com.cai.framework.dagger.ActivityScope;
import com.komutr.client.base.App;
import com.komutr.client.dagger.module.CommonModule;
import com.komutr.client.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by clarence on 2018/3/26
 */
@ActivityScope
@Component(dependencies = CommonComponent.class, modules = CommonModule.class)
public interface AppComponent {

    void inject(App app);
    void inject(MainActivity activity);

}
