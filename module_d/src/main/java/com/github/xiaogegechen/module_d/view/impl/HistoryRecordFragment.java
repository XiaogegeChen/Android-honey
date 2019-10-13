package com.github.xiaogegechen.module_d.view.impl;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.module_d.R;

import org.jetbrains.annotations.NotNull;

public class HistoryRecordFragment extends BaseFragment {

    private ImageView mDeleteImageView;
    private RecyclerView mRecyclerView;

    @Override
    public void initData() {

    }

    @Override
    public void initView(@NotNull View view) {
        mDeleteImageView = view.findViewById(R.id.module_d_fragment_history_record_delete_icon);
        mRecyclerView = view.findViewById(R.id.module_d_fragment_history_record_recycler_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_d_fragment_history_record;
    }
}
