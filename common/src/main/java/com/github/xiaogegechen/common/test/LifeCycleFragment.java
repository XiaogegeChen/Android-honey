package com.github.xiaogegechen.common.test;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.util.LogUtil;

import org.jetbrains.annotations.NotNull;

/**
 * 测试声明周期用的fragment
 */
public abstract class LifeCycleFragment extends BaseFragment {

    private static final String TAG = "LifeCycleFragment";

    @Override
    public void onAttach(@org.jetbrains.annotations.Nullable Context context) {
        LogUtil.d(TAG, "onAttach");
        super.onAttach(context);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        LogUtil.d(TAG, "onAttachFragment");
        super.onAttachFragment(childFragment);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @org.jetbrains.annotations.Nullable ViewGroup container, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        LogUtil.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        LogUtil.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        LogUtil.d(TAG, "onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        LogUtil.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        LogUtil.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        LogUtil.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        LogUtil.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        LogUtil.d(TAG, "onDetach");
        super.onDetach();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LogUtil.d(TAG, "onHiddenChanged. hidden -> " + hidden);
        super.onHiddenChanged(hidden);
    }
}
