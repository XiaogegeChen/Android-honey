package com.github.xiaogegechen.weather.model.event;

/**
 * 当首页已选城市的recyclerView 的子项被点击时发出该事件，该事件只允许{@link com.github.xiaogegechen.weather.view.impl.WeatherFragment}
 * 接收并消费
 */
public class NotifySelectedCitiesRvItemClickedEvent {
    private int mItemPosition;

    public int getItemPosition() {
        return mItemPosition;
    }

    public void setItemPosition(int itemPosition) {
        mItemPosition = itemPosition;
    }
}
