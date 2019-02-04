//package com.xiaogege.honey.adapter;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.view.ViewGroup;
//
//import java.util.List;
//
//public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
//    public List<Fragment> mFragmentList;
//
//    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
//        super(fm);
//        mFragmentList=fragmentList;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//
//        return mFragmentList==null?null:mFragmentList.get(position);
//    }
//
//    @Override
//    public int getCount() {
//        return mFragmentList==null?0:mFragmentList.size();
//    }
//
//}

package com.xiaogege.honey.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public List<Fragment> mFragmentList;
    private FragmentManager mFragmentManager;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        mFragmentList=fragmentList;
        mFragmentManager=fm;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        fragment = mFragmentList.get(position);
        Bundle bundle = new Bundle ();
        bundle.putString("id",""+position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mFragmentList==null?0:mFragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment)super.instantiateItem(container,position);
        mFragmentManager.beginTransaction().show(fragment).commit();
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Fragment fragment = mFragmentList.get(position);
        mFragmentManager.beginTransaction().hide(fragment).commit();
    }
}
