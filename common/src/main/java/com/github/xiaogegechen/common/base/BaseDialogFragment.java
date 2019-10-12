package com.github.xiaogegechen.common.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.*;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.github.xiaogegechen.common.R;

/**
 * DialogFragment 基类，抽取了公共配置。
 * 添加控制位，使show()和dismiss()可以安全调用,不会引发异常
 * 但是如果子类在没有调用dismiss()的情况下就取消了改DialogFragment
 * （比如按返回键），那么需要使用setAdded(boolean)更新标志位
 */
public abstract class BaseDialogFragment extends DialogFragment {

    // 标记位，标志是否
    protected boolean mIsAdded = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // root不能为空，因为framework需要使用它的layoutParams
        ViewGroup root = new FrameLayout(getContext());
        ViewGroup.LayoutParams rootParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        root.setLayoutParams(rootParams);
        View view = LayoutInflater.from(getContext()).inflate(getLayoutId(), root, false);
        initView(view);
        // dialog相关配置
        AppCompatDialog dialog = new AppCompatDialog (getContext (), R.style.common_base_dialog_style);
        dialog.setContentView (view);
        WindowManager.LayoutParams params = dialog.getWindow ().getAttributes ();
        params.gravity = Gravity.CENTER;
        params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.windowAnimations = R.style.common_dialog_bottom_anim;
        dialog.getWindow ().setAttributes (params);
        // 子类扩展的dialog设置
        setDialog(dialog);
        return dialog;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if(!mIsAdded){
            super.show(manager, tag);
            mIsAdded = true;
        }
    }

    @Override
    public void dismiss() {
        if(mIsAdded){
            super.dismiss();
            mIsAdded = false;
        }
    }

    /**
     * 手动设置标志位。 如果该DialogFragment在不经过show()显示或不经过dismiss()隐藏
     * 需要调用该方法重新设置标志位的状态
     * @param isAdded 当前DialogFragment是否加入了fm中
     */
    public void setAdded(boolean isAdded){
        mIsAdded = isAdded;
    }

    /**
     * 判断是否已经添加进fm
     * @return true 如果已经添加进fm
     */
    public boolean hasBeenAdded(){
        return mIsAdded;
    }

    // dialog的布局id
    public abstract int getLayoutId();

    // dialog布局的view初始化
    public abstract void initView(View view);

    // dialog样式设置
    public abstract void setDialog(Dialog dialog);
}
