package com.github.xiaogegechen.module_b.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import com.github.xiaogegechen.common.Constants;
import com.github.xiaogegechen.module_b.view.ConstellationDetailActivity;
import com.github.xiaogegechen.module_b.view.IFragmentBView;
import org.jetbrains.annotations.NotNull;

public class FragmentBPresenterImpl implements IFragmentBPresenter {

    private IFragmentBView mFragmentBView;
    private Context mContext;

    public FragmentBPresenterImpl(Context context) {
        mContext = context;
    }

    @Override
    public void gotoDetailActivity(Parcelable param) {
        Intent intent = new Intent(mContext, ConstellationDetailActivity.class);
        intent.putExtra(Constants.INTENT_PARAM_NAME, param);
        mContext.startActivity(intent);
    }

    @Override
    public void attach(IFragmentBView iFragmentBView) {
        mFragmentBView = iFragmentBView;
    }

    @Override
    public void detach() {
        mFragmentBView = null;
    }
}
