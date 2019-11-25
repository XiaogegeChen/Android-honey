package com.github.xiaogegechen.module_d.view;

import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.module_d.model.json.ExpressJSON;

public interface IExpressActivityView extends IBaseView {
    /**
     * 显示查询到的信息
     * @param express 从网络拿到的数据
     */
    void showInformation(ExpressJSON express);

    /**
     * 显示输入框，同时打开历史记录
     */
    void showEditText();

    /**
     * 关闭输入框， 同时关闭历史记录
     */
    void hideEditText();

    /**
     * 显示错误页面
     * @param errorMsg 错误信息
     */
    void showErrorPage(String errorMsg);
}
