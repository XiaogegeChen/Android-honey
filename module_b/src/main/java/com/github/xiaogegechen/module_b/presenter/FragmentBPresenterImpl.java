package com.github.xiaogegechen.module_b.presenter;

import android.content.Context;
import com.github.xiaogegechen.module_b.view.IFragmentBView;
import org.jetbrains.annotations.NotNull;

public class FragmentBPresenterImpl implements IFragmentBPresenter {

    private IFragmentBView mFragmentBView;
    private Context mContext;

    public FragmentBPresenterImpl(Context context) {
        mContext = context;
    }

    @Override
    public void gotoDetailActivity(String constellationName) {

    }

    @Override
    public void attach(@NotNull IFragmentBView iFragmentBView) {
        mFragmentBView = iFragmentBView;
    }

    @Override
    public void detach() {
        mFragmentBView = null;
    }
}
