package com.github.xiaogegechen.bing.view;

import com.github.xiaogegechen.bing.model.Module;
import com.github.xiaogegechen.common.base.IBaseView;

import java.util.List;

public interface IBingFragmentView extends IBaseView {

    /**
     * 将数据显示在recyclerView中
     *
     * @param moduleList 请求到的数据
     */
    void showModuleAndTopic(List<Module> moduleList);

}
