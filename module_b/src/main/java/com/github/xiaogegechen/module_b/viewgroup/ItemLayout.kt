package com.github.xiaogegechen.module_b.viewgroup

import android.content.Context
import android.widget.LinearLayout
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.TextView
import com.github.xiaogegechen.module_b.R

class ItemLayout constructor(context: Context, attr: AttributeSet?, defStyle: Int):LinearLayout(context, attr, defStyle) {

    constructor(context: Context, attr: AttributeSet?):this(context, attr, 0)
    constructor(context: Context):this(context, null)

    var value: TextView? = null
    var name:TextView? = null

    init {

        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.module_b_constellation_item, this)

        // 拿到控件
        name = findViewById<TextView>(R.id.itemName)
        value = findViewById(R.id.itemValue)

        // 拿到资源
        val typeArray = context.obtainStyledAttributes(attr, R.styleable.ItemLayout)
        val nameText = typeArray.getString(R.styleable.ItemLayout_itemLayoutName)
        val valueText = typeArray.getString(R.styleable.ItemLayout_itemLayoutValue)

        name?.text = nameText
        value?.text = valueText

        typeArray.recycle()
    }
}