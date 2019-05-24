package com.github.xiaogegechen.common.util

import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ImageUtil {

    private const val TAG = "ImageUtil"

    fun displayImage(param: ImageParam){
        if(checkParam(param)){
            Glide.with(param.context!!)
                .load(param.url)
                .apply(RequestOptions.errorOf(param.error).placeholder(param.placeholder))
                .into(param.imageView!!)
        }
    }

    private fun checkParam(param: ImageParam): Boolean{
        if(param.context == null || param.imageView == null){
            Log.e(TAG, "imageView or context is null while display image")
            return false
        }
        return true
    }

}