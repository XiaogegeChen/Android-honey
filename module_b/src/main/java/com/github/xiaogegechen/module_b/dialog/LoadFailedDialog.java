package com.github.xiaogegechen.module_b.dialog;

import android.app.Dialog;
import android.view.View;
import com.github.xiaogegechen.common.base.BaseDialogFragment;
import com.github.xiaogegechen.design.viewgroup.MessageDialogLayout;
import com.github.xiaogegechen.module_b.R;

/**
 * 加载失败的dialogFragment
 */
public class LoadFailedDialog extends BaseDialogFragment {

    private OnButtonClickListener mOnButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        mOnButtonClickListener = onButtonClickListener;
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_b_dialog_load_constellation_failed;
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
    }

    // 点击监听
    public interface OnButtonClickListener{
        void onExitClick(View view);
        void onRetryClick(View view);
    }
}
