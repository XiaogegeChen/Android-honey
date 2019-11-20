package com.github.xiaogegechen.main.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

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

public class FragmentRight extends EventBusFragment {

    private static final String TAG = "FragmentRight";

    private static final int DURATION = 100;
    private static final Interpolator INTERPOLATOR = new LinearInterpolator();

    private MyMenuView mMyMenuView;
    private FloatingActionButton mFloatingActionButton;
    private ImageView mFloatingActionButtonIcon;

    private ObjectAnimator mFloatingActionButtonIconOpenAnimator;
    private ObjectAnimator mFloatingActionButtonIconCloseAnimator;
    private MyMenuViewAdapter mMyMenuViewAdapter;
    private List<BaseFragment> mFragmentList;

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
        initFragments();
        initAnimator();
        initMenuView();
        mFloatingActionButton.setOnClickListener(v -> openMenuView());
    }

    private void initFragments(){
        mFragmentList = new ArrayList<>();
        BaseFragment fragmentA = (BaseFragment) ARouter.getInstance().build(MODULE_A_FRAGMENT_A_PATH).navigation();
        BaseFragment fragmentB = (BaseFragment) ARouter.getInstance().build(MODULE_B_FRAGMENT_B_PATH).navigation();
        BaseFragment fragmentC = (BaseFragment) ARouter.getInstance().build(MODULE_C_FRAGMENT_C_PATH).navigation();
        BaseFragment fragmentD = (BaseFragment) ARouter.getInstance().build(MODULE_D_FRAGMENT_D_PATH).navigation();
        mFragmentList.add(fragmentA);
        mFragmentList.add(fragmentB);
        mFragmentList.add(fragmentC);
        mFragmentList.add(fragmentD);
        Collections.reverse(mFragmentList);
        // 添加进指定区域
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        for (BaseFragment fragment : mFragmentList) {
            transaction.add(R.id.main_fragment_right_container, fragment);
        }
        transaction.commit();
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

    private void closeMenuView(){
        // 关闭menuView，图标旋转， fab显示
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
                mFloatingActionButtonIconCloseAnimator.start();
            }
        });
        mMyMenuView.close();
    }

    private void openMenuView(){
        // fab 先消失，图标旋转， menuView展开
        mFloatingActionButtonIconOpenAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
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
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        // 先隐藏上一个fragment,lastSelectedPosition 可能是-1,无效，要排除
        if(lastSelectedPosition >= 0){
            transaction.hide(mFragmentList.get(lastSelectedPosition));
        }
        // 显示需要的fragment
        transaction.show(mFragmentList.get(currentSelectedPosition));
        // 提交事务
        transaction.commit();
    }
}
