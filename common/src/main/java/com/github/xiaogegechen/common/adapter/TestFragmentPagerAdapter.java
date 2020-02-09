package com.github.xiaogegechen.common.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.github.xiaogegechen.common.base.BaseFragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class TestFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> mFragmentList;

    public TestFragmentPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        mFragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList == null ? null : mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList == null ? 0 : mFragmentList.size();
    }

    @Override
    public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
        super.destroyItem(container, position, object);
        container.removeView(((BaseFragment)object).getView());
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
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
