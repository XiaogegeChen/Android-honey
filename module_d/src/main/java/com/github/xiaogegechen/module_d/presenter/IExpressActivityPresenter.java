package com.github.xiaogegechen.module_d.presenter;

import com.github.xiaogegechen.common.base.IBasePresenter;
import com.github.xiaogegechen.module_d.model.json.ExpressJSON;
import com.github.xiaogegechen.module_d.model.ExpressMotionItem;
import com.github.xiaogegechen.module_d.view.IExpressActivityView;

import java.util.List;

public interface IExpressActivityPresenter extends IBasePresenter<IExpressActivityView> {

    /**
     * 查询订单信息，基本没有次数限制，
     * @param expressNumber 订单号
     */
    void queryExpressMessage(String expressNumber);

    /**
     * 从 ExpressJSON 解析出 ExpressMotionItem 的list
     * @param expressJSON expressJSON
     * @return ExpressMotionItem 的list
     */
    List<ExpressMotionItem> convertExpressJSON2ItemList(ExpressJSON expressJSON);
}
