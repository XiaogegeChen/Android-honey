package com.github.xiaogegechen.module_b

internal class Constants {
    companion object{

        const val ERROR = "请求出错"

        // 替换baseurl的头名称
        const val OKHTTP_HEAD_NAME = "okhttp_head_name"

        // 聚合数据星座查询相关
        const val JUHE_CONSTELLATION_HEAD_KEY = "juhe_head_key"
        private const val JUHE_CONSTELLATION_BASE_URL = "http://web.juhe.cn:8080/"
        const val JUHE_CONSTELLATION_ACCENT_KEY="76e853cb07c75138cf58ccaf765cc63e"

        // 背景图url地址
        const val CONSTELLATION_BG_URL = "https://android-honey-picture-everyday-1259261332.cos.ap-beijing.myqcloud.com/bing.jpg"

        // xml key集合
        const val XML_KEY_TODAY_MODULE_B = "xml_key_today_module_b"
        const val XML_KEY_WEEK_MODULE_B = "xml_key_week_module_b"
        const val XML_KEY_YEAR_MODULE_B = "xml_key_year_module_b"

        // baseurl替换表
        val sMap: MutableMap<String, String> = HashMap()
        init {
            sMap[JUHE_CONSTELLATION_HEAD_KEY] = JUHE_CONSTELLATION_BASE_URL
        }
    }
}