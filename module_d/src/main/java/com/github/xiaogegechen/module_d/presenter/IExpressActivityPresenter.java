package com.github.xiaogegechen.module_d.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_d.view.IExpressActivityView;

public interface IExpressActivityPresenter extends IBasePresenter<IExpressActivityView> {

    /**
     * 查询订单信息，基本没有次数限制，
     * @param expressNumber 订单号
     */
    void queryExpressMessage(String expressNumber);
}
