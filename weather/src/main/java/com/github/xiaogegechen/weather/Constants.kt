package com.github.xiaogegechen.weather

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
        const val XML_KEY_ALLOW_BG_CHANGE = "bg_change"

        // intent参数名
        const val INTENT_PARAM_CITY_INFO = "city_info"

        // 不同模块更新失败显示的信息
        const val FORECAST_ERROR_MESSAGE = "更新未来天气信息失败"
        const val NOW_ERROR_MESSAGE = "更新当前天气信息失败"
        const val HOURLY_ERROR_MESSAGE = "更新逐小时天气信息失败"
        const val LIFESTYLE_ERROR_MESSAGE = "更新生活建议信息失败"
        const val BG_IMAGE_ERROR_MESSAGE = "获取背景图失败"

        // 空数据
        const val NULL_DATA = "N/A"

        // 背景图改变时间间隔
        const val BG_CHANGE_INTERVAL = 10000
    }
}