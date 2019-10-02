package com.github.xiaogegechen.module_d;

public class Constants {
    // 聚合数据相关
    public static final String JUHE_BOOK_BASE_URL = "http://apis.juhe.cn/";
    public static final String JUHE_BOOK_ACCENT_KEY = "1aeba37d12156308bc6ee789044677c8";

    // 书目录表的缓存时间，超过这个时间将重新请求目录表（目前是7天）
    public static final long CATALOG_SESSION = 7L * 24L * 60L * 60L * 1000L;
    // 图书列表的缓存时间，超过这个时间将重新请求图书列表（目前是1天）
    public static final long BOOK_LIST_SESSION = 24L * 60L * 60L * 1000L;

    // sp的key
    public static final String XML_KEY_CATALOG_SESSION_MODULE_D = "catalog_session_module_d";
    public static final String XML_KEY_BOOK_LIST_SESSION_MODULE_D = "book_list_session_module_d";

    public static final String UNKNOWN_ERROR = "未知错误";
    public static final String QUERY_DATA_FAILED = "更新数据失败";
}
