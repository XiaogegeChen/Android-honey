package com.github.xiaogegechen.design

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView

class TitleBar(context: Context, attr: AttributeSet?, defStyle: Int): FrameLayout(context, attr, defStyle) {
    constructor(context: Context, attr: AttributeSet?):this(context, attr, 0)
    constructor(context: Context):this(context, null)

    private var mListener: OnArrowClickListener? = null
    private var mTitle: TextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.design_title_bar, this)
        val leftImage: ImageView? = findViewById(R.id.title_bar_left)
        val rightImage: ImageView? = findViewById(R.id.title_bar_right)
        mTitle = findViewById(R.id.title_bar_title)

        val typeArray = context.obtainStyledAttributes(attr, R.styleable.TitleBar)
        val leftVisibility = typeArray.getBoolean(R.styleable.TitleBar_left_visibility, false)
        val rightVisibility = typeArray.getBoolean(R.styleable.TitleBar_right_visibility, false)
        val titleText = typeArray.getString(R.styleable.TitleBar_title_bar_title)

        if(leftVisibility){
            leftImage?.visibility = View.VISIBLE
            leftImage?.setOnClickListener {
                mListener?.onLeftClick()
            }
        }else{
            leftImage?.visibility = View.INVISIBLE
        }

        if(rightVisibility){
            rightImage?.visibility = View.VISIBLE
            rightImage?.setOnClickListener {
                mListener?.onRightClick()
            }
        }else{
            rightImage?.visibility = View.INVISIBLE
        }

        mTitle?.text = titleText
        typeArray.recycle()
    }

    fun setListener(listener: OnArrowClickListener){
        mListener = listener
    }

    fun setText(text: String?){
        mTitle?.text = text
    }

    interface OnArrowClickListener{
        fun onLeftClick()
        fun onRightClick()
    }
}