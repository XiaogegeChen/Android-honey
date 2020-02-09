package com.github.xiaogegechen.weather.model.event;

/**
 * 这个事件在{@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}中的Rv子项被点击时发出，并被
 * {@link com.github.xiaogegechen.weather.view.impl.ManageCityActivity}接收。
 */
public class NotifySelectedCityRvInMCActItemClickEvent {
    // 被点击城市的id
    private String mId;

    public NotifySelectedCityRvInMCActItemClickEvent(){}

    public NotifySelectedCityRvInMCActItemClickEvent(String id){
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }
}
