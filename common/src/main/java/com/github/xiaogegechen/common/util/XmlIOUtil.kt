package com.github.xiaogegechen.common.util

import android.content.Context
import android.preference.PreferenceManager

object XmlIOUtil {
    fun read(key: String, context: Context): String?{
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null)
    }

    fun write(key: String, value: String, context: Context){
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString(key, value)
        editor.apply()
    }

}