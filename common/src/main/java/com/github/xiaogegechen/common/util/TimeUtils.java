package com.github.xiaogegechen.common.util;

import com.github.xiaogegechen.common.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeUtils {
    /**
     * 判断一年是不是闰年
     * @param year 年份
     * @return true如果是闰年
     */
    public static boolean isLeap(long year) {
        return ((year & 3) == 0) && ((year % 100) != 0 || (year % 400) == 0);
    }

    /**
     * 拿到指定月分的天数
     * @param leapYear 是否是闰年
     * @param month 月份
     * @return 这个月的天数
     */
    public static int lengthOfMonth(boolean leapYear, int month) {
        switch (month) {
            case 2:
                return (leapYear ? 29 : 28);
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 31;
        }
    }

    /**
     * 根据原始时间推算其相对于今天的时间描述，比如今天是 2019.11.23（周六），那么 "2019-11-23" 将返回"今天"， "2019-11-24"
     * 将返回"明天"，"2019-11-25" 将返回"周一"，以此类推。
     *
     * @param originTime 原始时间， 如：2019-11-25
     * @return 时间相对于今天的描述，计算失败返回 "N/A"
     */
    public static String getTimeDescriptionFromOriginTime(String originTime){
        String result = Constants.NULL_DATA;
        try {
            // 今天
            Calendar today = new GregorianCalendar();
            today.setTime(new Date());
            int todayYear = today.get(Calendar.YEAR);
            int todayMonth = today.get(Calendar.MONTH) + 1;
            int todayDay = today.get(Calendar.DAY_OF_MONTH);
            int todayMonthLength = lengthOfMonth(isLeap(todayYear), todayMonth);
            // 计算明天的日期
            int tomorrowDay = todayDay + 1;
            int tomorrowMonth = todayMonth;
            int tomorrowYear = todayYear;
            if(tomorrowDay > todayMonthLength){
                tomorrowDay = 1;
                tomorrowMonth = todayMonth + 1;
                if(tomorrowMonth > 12){
                    tomorrowMonth = 1;
                    tomorrowYear = todayYear + 1;
                }
            }
            // 从originTime拆分出年月日
            String[] originTimeString = originTime.split("-");
            int originTimeYear = Integer.parseInt(originTimeString[0]);
            int originTimeMonth = Integer.parseInt(originTimeString[1]);
            int originTimeDay = Integer.parseInt(originTimeString[2]);
            if(todayYear == originTimeYear && todayMonth == originTimeMonth && todayDay == originTimeDay){
                return "今天";
            }

            if(tomorrowYear == originTimeYear && tomorrowMonth == originTimeMonth && tomorrowDay == originTimeDay){
                return "明天";
            }
            // 如果超过两天那就计算是周几
            Calendar originTimeCalendar = new GregorianCalendar();
            originTimeCalendar.set(originTimeYear, originTimeMonth - 1, originTimeDay);
            int weekValue = originTimeCalendar.get(Calendar.DAY_OF_WEEK);
            switch (weekValue){
                case Calendar.MONDAY:
                    result = "周一";
                    break;
                case Calendar.TUESDAY:
                    result = "周二";
                    break;
                case Calendar.WEDNESDAY:
                    result = "周三";
                    break;
                case Calendar.THURSDAY:
                    result = "周四";
                    break;
                case Calendar.FRIDAY:
                    result = "周五";
                    break;
                case Calendar.SATURDAY:
                    result = "周六";
                    break;
                case Calendar.SUNDAY:
                    result = "周日";
                    break;
                default:
                    break;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

}
