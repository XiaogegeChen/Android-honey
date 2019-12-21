package com.github.xiaogegechen.common.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

public class ShareUtils {
    /**
     * 分享一个文本
     *
     * @param content 要分享的内容
     * @param context 启动intent的context
     */
    public static void shareText(String content, Context context){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }

    /**
     * 分享一张图片，默认的没有标题和描述
     *
     * @param bitmap 待分享的bitmap
     * @param context context
     */
    public static void shareBitmap(Bitmap bitmap, Context context){
        shareBitmap(bitmap, context, null, null);
    }

    /**
     * 分享一张图片
     *
     * @param bitmap 待分享的bitmap
     * @param context context
     * @param title 标题
     * @param description 内容
     */
    public static void shareBitmap(Bitmap bitmap, Context context, String title, String description){
        //由文件得到uri
        Uri imageUri = Uri.parse(MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, title, description));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}
