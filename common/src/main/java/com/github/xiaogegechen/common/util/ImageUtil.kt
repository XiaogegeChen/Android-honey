package com.github.xiaogegechen.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object ImageUtil {

    private const val TAG = "ImageUtil"

    @Deprecated("do not suggest")
    fun displayImage(param: ImageParam){
        if(checkParam(param)){
            Glide.with(param.context!!)
                .load(param.url)
                .apply(RequestOptions.errorOf(param.error).placeholder(param.placeholder))
                .into(param.imageView!!)
        }
    }

    @Deprecated("do not suggest")
    private fun checkParam(param: ImageParam): Boolean{
        if(param.context == null || param.imageView == null){
            Log.e(TAG, "imageView or context is null while display image")
            return false
        }
        return true
    }

    /**
     * 存储一张照片到默认路径中，这个方法涉及到io，是阻塞方法
     *
     * @param bitmap bitmap
     * @param dirName 文件夹名
     * @param url 图片url
     * @param context context
     * @param callback 结果回调
     */
    @Deprecated("do not suggest")
     fun saveImage(bitmap: Bitmap, dirName: String, url: String, context: Context, callback: SaveImageCallback){
        val dir = File(context.getExternalFilesDir(null), dirName)
        if(!dir.exists()){
            dir.mkdirs()
        }
        val file = File(dir, MyTextUtils.md5DigestAsHex(url) + ".jpg")
        try{
            if(file.exists()){
                file.delete()
            }
            file.createNewFile()
            val fileOutputStream = FileOutputStream(file)
            bitmap.compress (Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.close()
            callback.onSuccess(file.absolutePath)
        }catch (e: Exception){
            e.printStackTrace()
            callback.onFailure(e.message)
        }
    }

    /**
     * 存储一张照片到默认路径中，这个方法涉及到io，是阻塞方法
     *
     * @param url 图片url
     * @param dirName 文件夹名
     * @param context context
     * @param callback 结果回调
     */
    @Deprecated("do not suggest")
     fun saveImage(url: String, dirName: String, context: Context, callback: SaveImageCallback){
        try {
            val uRL = URL(url)
            val connection: HttpURLConnection = uRL.openConnection() as HttpURLConnection
            val inputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            saveImage(bitmap, dirName, url, context, callback)
        } catch (e : Exception) {
            e.printStackTrace()
            callback.onFailure(e.message)
        }
    }

    /**
     * 保存图片的回调
     */
    @Deprecated("do not suggest")
     interface SaveImageCallback{
        /**
         * 保存图片成功的回调方法
         *
         * @param filePath 图片路径
         */
        fun onSuccess(filePath: String)

        /**
         * 失败的回调
         *
         * @param message
         */
        fun onFailure(message: String?)
    }
}