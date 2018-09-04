package com.komutr.client.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016-10-31.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> tabs;

    public MainPagerAdapter(FragmentManager fm, List<Fragment> tabs) {
        super(fm);
        this.tabs = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        return tabs != null ? tabs.get(position) : null;
    }

    @Override
    public int getCount() {
        return tabs != null ? tabs.size() : 0;
    }
}
