package com.github.xiaogegechen.module_b.presenter;

import android.os.Parcelable;
import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_b.view.IFragmentBView;

public interface IFragmentBPresenter extends IBasePresenter<IFragmentBView> {
    /**
     * 跳转到星座详情Activity
     * @param param 星座名和iconId
     */
    void gotoDetailActivity(Parcelable param);
}
