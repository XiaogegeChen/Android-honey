package com.github.xiaogegechen.design.viewgroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.xiaogegechen.design.R

class TitleBar(context: Context, attr: AttributeSet?, defStyle: Int): ConstraintLayout(context, attr, defStyle) {
    constructor(context: Context, attr: AttributeSet?):this(context, attr, 0)
    constructor(context: Context):this(context, null)

    private var mListener: OnArrowClickListener? = null
    private var mTitle: TextView? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.design_title_bar, this)
        val rootLayout: ViewGroup? = findViewById(R.id.title_bar_root)
        val leftImage: ImageView? = findViewById(R.id.title_bar_left)
        val rightImage: ImageView? = findViewById(R.id.title_bar_right)
        mTitle = findViewById(R.id.title_bar_title)

        val typeArray = context.obtainStyledAttributes(attr, R.styleable.TitleBar)
        val leftVisibility = typeArray.getBoolean(R.styleable.TitleBar_left_visibility, false)
        val rightVisibility = typeArray.getBoolean(R.styleable.TitleBar_right_visibility, false)
        val titleText = typeArray.getString(R.styleable.TitleBar_title_bar_title)
        val bgColor = typeArray.getColor(R.styleable.TitleBar_title_bar_bg_color, resources.getColor(R.color.design_color_accent))

        rootLayout?.setBackgroundColor(bgColor)

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

    fun getTextView(): TextView{
        return mTitle!!
    }

    interface OnArrowClickListener{
        fun onLeftClick()
        fun onRightClick()
    }
}