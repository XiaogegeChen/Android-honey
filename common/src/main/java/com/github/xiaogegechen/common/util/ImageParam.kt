package com.github.xiaogegechen.common.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView

class ImageParam private constructor(builder:Builder){

    val context: Context?
    val url: String?
    val error: Drawable?
    val placeholder: Drawable?
    val blurRadius: Int?
    val imageView: ImageView?
    val animationDuration: Int?

    init {
        context = builder.context
        url = builder.url
        error = builder.error
        placeholder = builder.placeholder
        blurRadius = builder.blurRadius
        imageView = builder.imageView
        animationDuration = builder.animationDuration
    }

    class Builder{

        var context:Context? = null
        var url: String? = null
        var error: Drawable? = null
        var placeholder: Drawable? = null
        var blurRadius: Int? = 0
        var imageView: ImageView? = null
        var animationDuration: Int? = 0

        fun context(context: Context?): Builder{
            this.context = context
            return this
        }

        fun url(url: String?): Builder{
            this.url = url
            return this
        }

        fun error(drawable: Drawable?): Builder{
            this.error = drawable
            return this
        }

        fun placeholder(drawable: Drawable?): Builder{
            this.placeholder = drawable
            return this
        }

        fun blurRadius(r: Int?): Builder{
            this.blurRadius = r
            return this
        }

        fun imageView(imageView: ImageView?): Builder{
            this.imageView = imageView
            return this
        }

        fun animationDuration(duration: Int?): Builder{
            this.animationDuration = duration
            return this
        }

        fun build(): ImageParam{
            return ImageParam(this)
        }
    }

}