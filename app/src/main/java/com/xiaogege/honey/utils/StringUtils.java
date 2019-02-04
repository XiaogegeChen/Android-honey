package com.xiaogege.honey.utils;

public class StringUtils {

    /**
     *处理字符串，不为空就返回这个字符串本身，如果为null就返回默认值"____"
     */
    public static String normalize(String s){
        if(s==null){
            return "____";
        }else{
            return s;
        }
    }

    /**
     * 判断字符串是否是纯数字的，如果是返回true,否则返回false
     */
    public static boolean isAllNumber(String s){
        for(int i=0;i<s.length ();i++){
            if(Integer.valueOf ( s.charAt (i))<48||Integer.valueOf ( s.charAt (i))>57){
                return false;
            }
        }
        return true;
    }
}
