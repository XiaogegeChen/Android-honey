package com.github.xiaogegechen.common.dialog;

import android.app.Dialog;
import android.view.KeyEvent;
import android.view.View;

import com.github.xiaogegechen.common.R;
import com.github.xiaogegechen.common.base.BaseDialogFragment;
import com.github.xiaogegechen.design.viewgroup.MessageDialogLayout;

/**
 * 加载失败的dialogFragment
 */
public class LoadFailedDialog extends BaseDialogFragment {

    private OnButtonClickListener mOnButtonClickListener;

    private OnBackPressedListener mOnBackPressedListener;

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        mOnBackPressedListener = onBackPressedListener;
    }

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_dialog_load_failed;
    }

    @Override
    public void initView(View view) {
        MessageDialogLayout messageDialogLayout = view.findViewById(R.id.module_b_dialog_load_constellation_failed);
        messageDialogLayout.setOnClickListener(new MessageDialogLayout.OnClickListener() {
            @Override
            public void onCancelClick(View view) {
                if (mOnButtonClickListener != null) {
                    mOnButtonClickListener.onExitClick(view);
                }
            }

            @Override
            public void onConfirmClick(View view) {
                if (mOnButtonClickListener != null) {
                    mOnButtonClickListener.onRetryClick(view);
                }
            }
        });
    }

    @Override
    public void setDialog(Dialog dialog) {
        // 点击空白不取消
        dialog.setCanceledOnTouchOutside (false);
        dialog.setOnKeyListener((dialog1, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mIsAdded = false;
                if (mOnBackPressedListener != null) {
                    mOnBackPressedListener.backPressed();
                }
            }
            return false;
        });
    }

    // 点击监听
    public interface OnButtonClickListener{
        void onExitClick(View view);
        void onRetryClick(View view);
    }

    /**
     * 当该 ProgressDialog 收到点击事件是的回调
     */
    public interface OnBackPressedListener{
        void backPressed();
    }
}
