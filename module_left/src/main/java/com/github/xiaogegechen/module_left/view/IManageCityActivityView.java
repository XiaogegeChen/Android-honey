package com.github.xiaogegechen.module_left.view;

import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.module_left.model.SelectedCity;

import java.util.List;

public interface IManageCityActivityView extends IBaseView {
    /**
     * 显示已经选择的城市列表
     * @param selectedCityList 已经选择的城市
     */
    void showSelectedCity(List<SelectedCity> selectedCityList);

    /**
     * 显示没有选择城市的提示界面
     */
    void showNothing();
}
