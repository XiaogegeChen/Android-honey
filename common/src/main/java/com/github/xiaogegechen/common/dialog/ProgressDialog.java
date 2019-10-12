package com.github.xiaogegechen.common.dialog;

import android.app.Dialog;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import com.github.xiaogegechen.common.R;
import com.github.xiaogegechen.common.base.BaseDialogFragment;

/**
 * 加载中的dialog
 */
public class ProgressDialog extends BaseDialogFragment {

    private OnBackPressedListener mOnBackPressedListener;

    @Override
    public int getLayoutId() {
        return R.layout.common_progress_dialog;
    }

    @Override
    public void initView(View view) {
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        mOnBackPressedListener = onBackPressedListener;
    }

    @Override
    public void setDialog(Dialog dialog) {
        dialog.setCanceledOnTouchOutside(false);
        // 因为可以按返回键取消，所以要监听返回键，从而更新状态标志位
        dialog.setOnKeyListener((dialog1, keyCode, event) -> {
            if(keyCode == KeyEvent.KEYCODE_BACK){
                mIsAdded = false;
                if (mOnBackPressedListener != null) {
                    mOnBackPressedListener.backPressed();
                }
            }
            return false;
        });
        WindowManager.LayoutParams params = dialog.getWindow ().getAttributes ();
        params.windowAnimations = android.R.style.Animation_Toast;
        dialog.getWindow ().setAttributes (params);
    }

    /**
     * 当该 ProgressDialog 收到点击事件是的回调
     */
    public interface OnBackPressedListener{
        void backPressed();
    }
}
