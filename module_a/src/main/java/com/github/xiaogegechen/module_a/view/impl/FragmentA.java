package com.github.xiaogegechen.module_a.view.impl;

import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.xiaogegechen.common.adapter.MyFragmentPagerAdapter;
import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.module_a.R;
import com.github.xiaogegechen.module_a.presenter.impl.FragmentAPresenterImpl;
import com.github.xiaogegechen.module_a.presenter.IFragmentAPresenter;
import com.github.xiaogegechen.module_a.view.IFragmentAView;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.xiaogegechen.common.arouter.ARouterMap.MODULE_A_FRAGMENT_A_PATH;

@Route(path = MODULE_A_FRAGMENT_A_PATH)
public class FragmentA extends BaseFragment implements IFragmentAView {

    private TabLayout mTabLayout;
    private Toolbar mToolbar;
    private ViewPager mViewPager;

    private IFragmentAPresenter mFragmentAPresenter;

    @Override
    public void initView(@NotNull View view) {
        // 不需要支持沉浸式，因此添加一个与状态栏等高的view，占位
        if(view instanceof LinearLayout){
            StatusBarUtils.fillStatusBarByView(
                    obtainContext(),
                    (LinearLayout) view,
                    obtainResources().getColor(R.color.module_a_photo_color_primary)
            );
        }
        mTabLayout = view.findViewById(R.id.tabLayoutA);
        mToolbar = view.findViewById(R.id.toolBarA);
        mViewPager = view.findViewById(R.id.viewPagerA);
    }

    @Override
    public void initData() {
        mFragmentAPresenter = new FragmentAPresenterImpl();
        mFragmentAPresenter.attach(this);
        // tabLayout标题
        String[] titleArray = obtainResources().getStringArray(R.array.module_a_tab_titles);
        for (String s : titleArray) {
            mTabLayout.addTab(mTabLayout.newTab().setText(s));
        }
        // toolbar图标点击监听
        mToolbar.setNavigationOnClickListener(v -> mFragmentAPresenter.notifyDrawerOpen());
        // viewPager适配器
        List<BaseFragment> fragmentList = new ArrayList<>();
        PictureListFragment fragmentFirst = new PictureListFragment();
        fragmentFirst.setType(PictureListFragment.TYPE_FIRST);
        fragmentList.add(fragmentFirst);
        PictureListFragment fragmentSecond = new PictureListFragment();
        fragmentSecond.setType(PictureListFragment.TYPE_SECOND);
        fragmentList.add(fragmentSecond);
        PictureListFragment fragmentThird = new PictureListFragment();
        fragmentThird.setType(PictureListFragment.TYPE_THIRD);
        fragmentList.add(fragmentThird);
        mViewPager.setAdapter(new MyFragmentPagerAdapter(obtainActivity().getSupportFragmentManager(), fragmentList) {
            @Override
            public List<String> getTitle() {
                return Arrays.asList(titleArray);
            }
        });
        // viewPager与tabLayout绑定
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_a_fragment_a;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentAPresenter.detach();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showToast(String message) {
        ToastUtil.show(obtainContext(), message);
    }
}
