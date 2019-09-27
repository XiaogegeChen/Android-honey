package com.github.xiaogegechen.common.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.*;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialog;
import androidx.fragment.app.DialogFragment;
import com.github.xiaogegechen.common.R;

public abstract class BaseDialogFragment extends DialogFragment {

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

    // dialog的布局id
    public abstract int getLayoutId();

    // dialog布局的view初始化
    public abstract void initView(View view);

    // dialog样式设置
    public abstract void setDialog(Dialog dialog);
}
