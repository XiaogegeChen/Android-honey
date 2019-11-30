package com.github.xiaogegechen.main.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.base.EventBusFragment;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.library.MenuView;
import com.github.xiaogegechen.main.Consts;
import com.github.xiaogegechen.main.R;
import com.github.xiaogegechen.main.adapter.MyMenuViewAdapter;
import com.github.xiaogegechen.main.event.NotifyMenuClickEvent;
import com.github.xiaogegechen.main.widget.MyMenuView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.xiaogegechen.common.arouter.ARouterMap.MODULE_A_FRAGMENT_A_PATH;
import static com.github.xiaogegechen.common.arouter.ARouterMap.MODULE_B_FRAGMENT_B_PATH;
import static com.github.xiaogegechen.common.arouter.ARouterMap.MODULE_C_FRAGMENT_C_PATH;
import static com.github.xiaogegechen.common.arouter.ARouterMap.MODULE_D_FRAGMENT_D_PATH;
import static com.github.xiaogegechen.common.arouter.ARouterMap.BING_FRAGMENT_BING;
import static com.github.xiaogegechen.common.arouter.ARouterMap.WEATHER_FRAGMENT_WEATHER;

public class FragmentRight extends EventBusFragment {

    private static final String TAG = "FragmentRight";

    private static final int DURATION = 100;
    private static final Interpolator INTERPOLATOR = new LinearInterpolator();

    // fragment的路径集合，从右向左
    private static final List<String> FRAGMENT_PATH_LIST = new ArrayList<>();
    static {
        FRAGMENT_PATH_LIST.add(MODULE_A_FRAGMENT_A_PATH);
        FRAGMENT_PATH_LIST.add(MODULE_B_FRAGMENT_B_PATH);
        FRAGMENT_PATH_LIST.add(MODULE_C_FRAGMENT_C_PATH);
        FRAGMENT_PATH_LIST.add(MODULE_D_FRAGMENT_D_PATH);
        FRAGMENT_PATH_LIST.add(BING_FRAGMENT_BING);
        FRAGMENT_PATH_LIST.add(WEATHER_FRAGMENT_WEATHER);
        Collections.reverse(FRAGMENT_PATH_LIST);
    }

    private SparseArray<BaseFragment> mFragmentSparseArray;

    private MyMenuView mMyMenuView;
    private FloatingActionButton mFloatingActionButton;
    private ImageView mFloatingActionButtonIcon;

    private ObjectAnimator mFloatingActionButtonIconOpenAnimator;
    private ObjectAnimator mFloatingActionButtonIconCloseAnimator;
    private MyMenuViewAdapter mMyMenuViewAdapter;

    private FragmentManager mFragmentManager;

    @Override
    public void initView(@NotNull View view) {
        mMyMenuView = view.findViewById(R.id.main_fragment_right_menu_view);
        mFloatingActionButton = view.findViewById(R.id.main_fragment_right_fab);
        mFloatingActionButtonIcon = view.findViewById(R.id.main_fragment_right_fab_icon);
    }

    @Override
    public void initData() {
        mFragmentManager = getFragmentManager();
        mFragmentSparseArray = new SparseArray<>();
        initAnimator();
        initMenuView();
        mFloatingActionButton.setOnClickListener(v -> openMenuView());
    }

    private void initAnimator(){
        mFloatingActionButtonIconOpenAnimator = ObjectAnimator.ofFloat(mFloatingActionButtonIcon, View.ROTATION, 0f, 45f);
        mFloatingActionButtonIconOpenAnimator.setDuration(DURATION);
        mFloatingActionButtonIconOpenAnimator.setInterpolator(INTERPOLATOR);
        mFloatingActionButtonIconCloseAnimator = ObjectAnimator.ofFloat(mFloatingActionButtonIcon, View.ROTATION, 45f, 0f);
        mFloatingActionButtonIconCloseAnimator.setDuration(DURATION);
        mFloatingActionButtonIconCloseAnimator.setInterpolator(INTERPOLATOR);
    }

    private void initMenuView(){
        mFloatingActionButton.hide();
        mFloatingActionButtonIconOpenAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                closeMenuView();
                mFloatingActionButtonIconOpenAnimator.removeListener(this);
            }
        });
        mMyMenuViewAdapter = new MyMenuViewAdapter(Consts.MENU_ITEM_LIST, obtainActivity());
        mMyMenuView.setAdapter(mMyMenuViewAdapter);
        mMyMenuView.makeViewSelected(mMyMenuViewAdapter.getCount() - 2);
        showFragment(mMyMenuViewAdapter.getCount() - 2, -1);
        mFloatingActionButtonIconOpenAnimator.start();
    }

    /**
     * 关闭动画，分以下步骤：
     * 1. 关闭menuView
     * 2. 加号图标出现
     * 3. 加号图标旋转90度
     * 4. fab显示
     */
    private void closeMenuView(){
        mFloatingActionButtonIconCloseAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFloatingActionButton.show();
                mFloatingActionButtonIconCloseAnimator.removeListener(this);
            }
        });
        mMyMenuView.setCloseAnimatorListener(new MenuView.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                mMyMenuView.setVisibility(View.GONE);
                mFloatingActionButtonIcon.setVisibility(View.VISIBLE);
                mFloatingActionButtonIconCloseAnimator.start();
            }
        });
        mMyMenuView.close();
    }

    /**
     * 打开动画，分以下步骤：
     * 1. fab消失
     * 2. 加号图标旋转90度
     * 3. menuView展开
     * 4. 加号图标消失
     */
    private void openMenuView(){
        mMyMenuView.setVisibility(View.VISIBLE);
        mFloatingActionButtonIconOpenAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mMyMenuView.setOpenAnimatorListener(new MenuView.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        // menu动画开始之前就隐藏，避免视觉混乱
                        mFloatingActionButtonIcon.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {}
                });
                mMyMenuView.open();
                mFloatingActionButtonIconOpenAnimator.removeListener(this);
            }
        });
        mFloatingActionButton.hide();
        mFloatingActionButtonIconOpenAnimator.start();
    }

    @Override
    public int getLayoutId() {
        return R.layout.main_fragment_right;
    }

    @Subscribe
    public void onHandleNotifyMenuClickEvent(NotifyMenuClickEvent event){
        int currentSelectedPosition = event.getCurrentSelectedPosition();
        int lastSelectedPosition = event.getLastSelectedPosition();
        LogUtil.d(TAG, "currentSelectedPosition : " + currentSelectedPosition + ", lastSelectedPosition: " + lastSelectedPosition);
        boolean isCloseItem = (currentSelectedPosition == mMyMenuViewAdapter.getCount() - 1);
        if(!isCloseItem){
            showFragment(currentSelectedPosition, lastSelectedPosition);
        }else{
            // 点击关闭需要close
            closeMenuView();
        }
    }

    /**
     * 显示fragment，会隐藏上一个fragment，并显示需要显示的fragment
     * @param currentSelectedPosition 上一个fragment的位置
     * @param lastSelectedPosition 需要显示的fragment的位置
     */
    private void showFragment(int currentSelectedPosition, int lastSelectedPosition){

        LogUtil.d(TAG, "mFragmentSparseArray -> " + mFragmentSparseArray.toString());

        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // 先隐藏上一个fragment,lastSelectedPosition 可能是-1,无效，要排除
        if(lastSelectedPosition >= 0){
            Fragment lastFragment = addFragmentIfNeeded(lastSelectedPosition);
            LogUtil.d(TAG, "lastFragment -> " + lastFragment.toString());
            transaction.hide(lastFragment);
        }
        // 显示需要的fragment
        Fragment currentFragment = addFragmentIfNeeded(currentSelectedPosition);
        LogUtil.d(TAG, "currentFragment -> " + currentFragment.toString());
        transaction.show(currentFragment);
        // 提交事务
        transaction.commit();
    }

    /**
     * 获取指定位置的fragment，如果这个fragment还没有加进map中，先加进map中，再返回这个fragment，实现懒加载
     *
     * @param position 指定位置
     * @return 这个位置的fragment
     */
    private BaseFragment addFragmentIfNeeded(int position){
        BaseFragment fragment = mFragmentSparseArray.get(position);
        if(fragment == null){
            fragment = (BaseFragment) ARouter.getInstance().build(FRAGMENT_PATH_LIST.get(position)).navigation();
            FragmentTransaction transaction = mFragmentManager.beginTransaction();
            transaction.add(R.id.main_fragment_right_container, fragment);
            transaction.hide(fragment);
            transaction.commit();
            mFragmentSparseArray.put(position, fragment);
        }
        return fragment;
    }
}
