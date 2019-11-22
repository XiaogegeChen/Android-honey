package com.github.xiaogegechen.module_left

internal class Constants {
    companion object{
        // 背景图和头像url
        const val SCENERY_BG_URL = "https://android-honey-picture-everyday-1259261332.cos.ap-beijing.myqcloud.com/scenery.jpg"
        const val HEAD_IMAGE_URL = "https://android-honey-picture-everyday-1259261332.cos.ap-beijing.myqcloud.com/head.jpg"

        //base_url
        const val WEATHER_KEY = "bf843e92395349b1896c86069bc0b872"
        const val WEATHER_SEARCH_CITY_URL = "https://search.heweather.net/"
        const val WEATHER_QUERY_BASIC_WEATHER_URL = "https://free-api.heweather.net/s6/weather/"
        const val WEATHER_ICON_URL = "https://cdn.heweather.com/cond_icon/"

        // webView的url
        const val BLOG_ACTIVITY_URL = "https://me.csdn.net/qq_40909351"
        const val GITHUB_ACTIVITY_URL = "https://github.com/XiaogegeChen"

        // xml_key
        const val XML_KEY_SELECTED_CITY_LIST_MODULE_LEFT = "select_cities_module_left"

        // intent参数名
        const val INTENT_PARAM_CITY_INFO = "city_info"

        // 不同模块更新失败显示的信息
        const val FORECAST_ERROR_MESSAGE = "更新未来七天天气信息失败"
        const val NOW_ERROR_MESSAGE = "更新当前天气信息失败"
        const val HOURLY_ERROR_MESSAGE = "更新逐小时天气信息失败"
        const val SUGGESTION_ERROR_MESSAGE = "更新生活建议信息失败"
        const val AQI_ERROR_MESSAGE = "更新AQI信息失败"
        const val IMAGE_ERROR_MESSAGE = "更新图片失败"

        // 空数据
        const val NULL_DATA = "N/A"
    }
}