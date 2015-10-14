package com.throne.emm.Fragment;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter{
    private List<Fragment> mDatas;
    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> mDatas) {
        super(fm);
        this.mDatas = mDatas;
    }

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        return mDatas.get(arg0);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

}
