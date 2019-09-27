package com.github.xiaogegechen.design.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.github.xiaogegechen.design.R;

/**
 * messageDialog的layout，使用者应该自定义配置并在
 * dialogFragment中加载这个布局
 */
public class MessageDialogLayout extends LinearLayout {

    private TextView mTitleTextView;
    private TextView mContentTextView;
    private TextView mConfirmTextView;
    private TextView mCancelTextView;

    private OnClickListener mOnClickListener;

    public MessageDialogLayout(Context context) {
        this(context, null);
    }

    public MessageDialogLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MessageDialogLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.design_message_dialog, this);
        // 属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MessageDialogLayout);
        String title = typedArray.getString(R.styleable.MessageDialogLayout_message_dialog_title);
        String content = typedArray.getString(R.styleable.MessageDialogLayout_message_dialog_content);
        String cancel = typedArray.getString(R.styleable.MessageDialogLayout_message_dialog_cancel);
        String confirm = typedArray.getString(R.styleable.MessageDialogLayout_message_dialog_confirm);
        typedArray.recycle();
        mTitleTextView = findViewById(R.id.design_message_dialog_title);
        mContentTextView = findViewById(R.id.design_message_dialog_content);
        mCancelTextView = findViewById(R.id.design_message_dialog_cancel);
        mConfirmTextView = findViewById(R.id.design_message_dialog_confirm);
        // 显示
        mTitleTextView.setText(title);
        mContentTextView.setText(content);
        mConfirmTextView.setText(confirm);
        mCancelTextView.setText(cancel);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        mCancelTextView.setOnClickListener(v -> mOnClickListener.onCancelClick(mCancelTextView));
        mConfirmTextView.setOnClickListener(v -> mOnClickListener.onConfirmClick(mCancelTextView));
    }

    public void setTitle(String title){
        mTitleTextView.setText(title);
        invalidate();
    }

    public void setContent(String content){
        mContentTextView.setText(content);
        invalidate();
    }

    public void setConfirm(String confirm){
        mConfirmTextView.setText(confirm);
        invalidate();
    }

    public void setCancel(String cancel){
        mCancelTextView.setText(cancel);
        invalidate();
    }

    /**
     * 点击监听
     */
    public interface OnClickListener{
        void onCancelClick(View view);
        void onConfirmClick(View view);
    }
}
