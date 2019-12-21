package com.github.xiaogegechen.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

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

    /**
     * md5加密
     *
     * @param dataStr 原始字符串
     * @return 生成的加密后的字符串，如果遇到异常返回null
     */
    public static String md5DigestAsHex(String dataStr) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes(StandardCharsets.UTF_8));
            byte[] s = m.digest();
            StringBuilder result = new StringBuilder();
            for (byte b : s) {
                result.append(Integer.toHexString((0x000000FF & b) | 0xFFFFFF00).substring(6));
            }
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
