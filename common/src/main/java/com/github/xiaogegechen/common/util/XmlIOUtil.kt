package com.github.xiaogegechen.common.util

import android.content.Context
import android.preference.PreferenceManager

/**
 * sp工具类，值存放在默认的sp中
 */
object XmlIOUtil {
    /**
     * 拿到string
     */
    fun read(key: String, context: Context): String?{
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null)
    }

    /**
     * 写入string
     */
    fun write(key: String, value: String, context: Context){
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString(key, value)
        editor.apply()
    }

    /**
     * 拿到stringSet
     */
    fun readStringSet(key: String, context: Context): Set<String>?{
        return PreferenceManager.getDefaultSharedPreferences(context).getStringSet(key, null)
    }

    /**
     * 写入stringSet
     */
    fun writeStringSet(key: String, value: Set<String>, context: Context){
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putStringSet(key, value)
        editor.apply()
    }

    /**
     * 拿到long
     */
    fun readLong(key: String, context: Context): Long{
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, 0)
    }

    /**
     * 写入long
     */
    fun writeLong(key: String, value: Long, context: Context){
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putLong(key, value)
        editor.apply()
    }

}