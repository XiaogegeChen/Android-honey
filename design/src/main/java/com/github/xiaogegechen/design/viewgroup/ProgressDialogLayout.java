package com.github.xiaogegechen.design.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.github.xiaogegechen.design.R;

public class ProgressDialogLayout extends LinearLayout {

    private TextView mTextView;

    public ProgressDialogLayout(Context context) {
        this(context, null);
    }

    public ProgressDialogLayout(Context context, @Nullable AttributeSet attrs) {
        this (context, attrs, 0);
    }

    public ProgressDialogLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.design_progress_dialog, this);
        mTextView = findViewById(R.id.design_progress_dialog_text);
        // 属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ProgressDialogLayout);
        String title = typedArray.getString(R.styleable.ProgressDialogLayout_progress_dialog_text);
        if (title != null) {
            mTextView.setText(title);
        }
        typedArray.recycle();
    }

    public void setText(String text) {
        if (text != null) {
            mTextView.setText(text);
            invalidate();
        }
    }
}
