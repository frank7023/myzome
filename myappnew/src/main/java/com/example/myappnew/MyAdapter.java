package com.example.myappnew;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class MyAdapter extends FragmentPagerAdapter {
    private List<Fragment> list;
    private List<ClassifyEntity>titles;

    public MyAdapter(FragmentManager fm, List<Fragment> list, List<ClassifyEntity> titles) {
        super(fm);
        this.list = list;
        this.titles = titles;
    }



    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles.get(position).getName();
    }
}
