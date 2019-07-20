package com.github.xiaogegechen.module_b.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.github.xiaogegechen.module_b.R;

public class EnterItemLayout extends LinearLayout {

    public EnterItemLayout(Context context) {
        this(context, null);
    }

    public EnterItemLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EnterItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.module_b_enter_item, this);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EnterItemLayout);
        int imageId = typedArray.getResourceId(R.styleable.EnterItemLayout_enter_item_image, R.drawable.baiyangzuo);
        String name = typedArray.getString(R.styleable.EnterItemLayout_enter_item_constellation_name);
        String date = typedArray.getString(R.styleable.EnterItemLayout_enter_item_constellation_date);
        ImageView imageView = findViewById(R.id.module_b_enter_item_image);
        TextView nameTextView = findViewById(R.id.module_b_enter_item_constellation_name);
        TextView dateTextView = findViewById(R.id.module_b_enter_item_constellation_date);
        imageView.setImageResource(imageId);
        nameTextView.setText(name);
        dateTextView.setText(date);
        typedArray.recycle();
    }
}
