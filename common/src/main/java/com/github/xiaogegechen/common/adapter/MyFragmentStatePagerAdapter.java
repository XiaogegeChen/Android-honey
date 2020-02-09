package com.github.xiaogegechen.common.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.github.xiaogegechen.common.base.BaseFragment;

import java.util.List;

public abstract class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> mFragmentList;

    public MyFragmentStatePagerAdapter(FragmentManager fm, List<BaseFragment> list) {
        super(fm);
        mFragmentList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(0);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(getTitle () != null){
            return getTitle ().get (position);
        }
        return null;
    }

    //设置title,如果不是和TabLayout绑定,返回null
    public abstract List<String> getTitle();
}
