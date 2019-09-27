package com.github.xiaogegechen.common.dialog;

import android.app.Dialog;
import android.view.View;
import android.view.WindowManager;
import com.github.xiaogegechen.common.R;
import com.github.xiaogegechen.common.base.BaseDialogFragment;

/**
 * 加载中的dialog
 */
public class ProgressDialog extends BaseDialogFragment {
    @Override
    public int getLayoutId() {
        return R.layout.common_progress_dialog;
    }

    @Override
    public void initView(View view) {
    }

    @Override
    public void setDialog(Dialog dialog) {
        dialog.setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = dialog.getWindow ().getAttributes ();
        params.windowAnimations = android.R.style.Animation_Toast;
        dialog.getWindow ().setAttributes (params);
    }
}
