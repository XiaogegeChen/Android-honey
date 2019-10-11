package com.github.xiaogegechen.module_d.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_d.view.IExpressActivityView;

public interface IExpressActivityPresenter extends IBasePresenter<IExpressActivityView> {
    void queryExpressMessage(String expressNumber);
}
