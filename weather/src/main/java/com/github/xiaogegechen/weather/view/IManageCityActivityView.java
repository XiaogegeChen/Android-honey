package com.github.xiaogegechen.weather.view;

import com.github.xiaogegechen.common.base.IBaseView;
import com.github.xiaogegechen.weather.model.SelectedCityForRvInMCAct;

import java.util.List;

public interface IManageCityActivityView extends IBaseView {
    /**
     * 更改已选城市列表中的某一项数据为新数据
     * @param selectedCityForRvInMCAct 新数据
     */
    void changeItem(SelectedCityForRvInMCAct selectedCityForRvInMCAct);

    /**
     * 删除已选城市列表中的某一项
     * @param cityId 待删除的城市id
     */
    void removeItem(String cityId);

    /**
     * 向已选城市列表中添加一项，添加到末尾
     * @param selectedCityForRvInMCAct 新数据
     */
    void addItem(SelectedCityForRvInMCAct selectedCityForRvInMCAct);

    /**
     * 显示没有选择城市的提示界面
     */
    void showNothing();

    /**
     * 从recyclerView中移除对应的城市，通常是移除按钮被点击后调用
     * @param selectedCityForRvInMCAct city
     */
    void removeCity(SelectedCityForRvInMCAct selectedCityForRvInMCAct);
}
