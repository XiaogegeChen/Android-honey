package com.github.xiaogegechen.weather

import com.github.xiaogegechen.common.Constants

internal class Constants {
    companion object{
        //base_url
        const val WEATHER_KEY = "bf843e92395349b1896c86069bc0b872"
        const val WEATHER_SEARCH_CITY_URL = "https://search.heweather.net/"
        const val WEATHER_QUERY_BASIC_WEATHER_URL = "https://free-api.heweather.net/s6/weather/"
        const val WEATHER_ICON_URL = "https://cdn.heweather.com/cond_icon/"

        // xml_key
        const val XML_KEY_SELECTED_CITY_LIST_MODULE_LEFT = "select_cities_module_left"
        const val XML_KEY_LAST_TEMP_PREFIX = "last_temp_"

        // intent参数名
        const val INTENT_PARAM_FROM_WEATHER_FRAGMENT_TO_MANAGE_CITY_ACTIVITY = "ip_fwf_tmca"

        // 不同模块更新失败显示的信息
        const val FORECAST_ERROR_MESSAGE = "更新未来天气信息失败"
        const val NOW_ERROR_MESSAGE = "更新当前天气信息失败"
        const val HOURLY_ERROR_MESSAGE = "更新逐小时天气信息失败"
        const val LIFESTYLE_ERROR_MESSAGE = "更新生活建议信息失败"
        const val BG_IMAGE_ERROR_MESSAGE = "获取背景图失败"

        // 空数据
        const val NULL_DATA = Constants.NULL_DATA

        // 背景图改变时间间隔
        const val BG_CHANGE_INTERVAL = 10000
        const val ALLOW_BG_CHANGE = true
        // 轮播图切换时间间隔
        const val BANNER_INTERVAL = 3000
        // 轮播文字切换时间间隔
        const val BANNER_TEXT_VIEW_INTERVAL = 3000
        // 首页已选中城市Rv一屏最多显示的个数
        const val SELECTED_CITIES_RV_MAX_COUNT = 6
        const val BANNER_INDICATOR_RV_MAX_COUNT = 5
        // 天气更新的时间间隔，如果上次更新超过这个时间间隔，需要从服务器拉取新的数据
        const val WEATHER_REFRESH_INTERVAL = 1000 * 60 * 60 // ms
    }
}