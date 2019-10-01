package com.github.xiaogegechen.module_d;

public class Constants {
    // 聚合数据相关
    public static final String JUHE_BOOK_BASE_URL = "http://apis.juhe.cn/";
    public static final String JUHE_BOOK_ACCENT_KEY = "1aeba37d12156308bc6ee789044677c8";

    // 书目录表的缓存时间，超过这个时间将重新请求目录表（目前是7天）
    public static final long CATALOG_SESSION = 7L * 24L * 60L * 60L * 1000L;

    // sp的key
    public static final String XML_KEY_CATALOG_SESSION_MODULE_D = "xml_key_catalog_session_module_d";

    public static final String UNKNOWN_ERROR = "未知错误";
}
