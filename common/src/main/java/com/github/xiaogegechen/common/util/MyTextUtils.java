package com.github.xiaogegechen.common.util;

import android.os.Parcelable;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Locale;

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

    /**
     * 保留两位小数
     *
     * @param input 输入
     * @return 两位小数的字符串
     */
    public static String round(float input){
       return String.format(Locale.ENGLISH, "%.2f", input);
    }

    /**
     * 处理文件大小，如果大于1MB就按MB为单位，否则以kb为单位
     *
     * @param kb 大小kb
     * @return 文件大小
     */
    public static String handleFileSize(float kb){
        if(kb > 1024){
            return round(kb / 1024) + "MB";
        }else{
            return round(kb) + "kb";
        }
    }

    public static String printParcelableArray(Parcelable[] dataArray){
        if(dataArray.length == 0){
            return "[]";
        }

        StringBuilder builder = new StringBuilder();
        builder.append("total length -> ")
                .append(dataArray.length);
        builder.append(", data -> ").append("[");
        for (Parcelable parcelable : dataArray) {
            builder.append(parcelable.toString());
            builder.append(", ");
        }
        builder.append("]");
        return builder.toString();
    }
}
