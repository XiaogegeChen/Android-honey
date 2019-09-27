package com.github.xiaogegechen.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 文字处理工具类
 */
public class MyTextUtils {
    /**
     * urlEncode
     * @param cn 待编码字符串
     * @return 编码后的字符串
     */
    public static String urlEncode(String cn){
        String res = "";
        try {
            res = URLEncoder.encode(cn, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return res;
    }
}
