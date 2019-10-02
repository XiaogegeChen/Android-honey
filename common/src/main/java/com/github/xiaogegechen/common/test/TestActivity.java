package com.github.xiaogegechen.common.test;

import android.app.Activity;
import android.content.Intent;
import android.widget.TextView;

import com.github.xiaogegechen.common.R;
import com.github.xiaogegechen.common.base.AppConfig;
import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.design.viewgroup.TitleBar;

/**
 * 测试用的activity，包含一个滚动的textView，可以
 * 显示一些信息
 */
public class TestActivity extends BaseActivity {

    private static final String DEBUG_PARAM_NAME = "debug_param_name";

    private TitleBar mTitleBar;
    private TextView mTextView;

    @Override
    public void initData() {
        mTitleBar.setListener(new TitleBar.OnArrowClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {}
        });

        Intent intent = getIntent();
        String info = intent.getStringExtra(DEBUG_PARAM_NAME);
        mTextView.setText(info);
    }

    @Override
    public void initView() {
        mTitleBar = findViewById(R.id.common_activity_test_title_bar);
        mTextView = findViewById(R.id.common_activity_test_text_view);
    }

    @Override
    public int getLayoutId() {
        return R.layout.common_activity_test;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * 启动自身的方法,这个方法只在debug环境下有效，intent携带过多参数会有exception
     * 加try-catch，防止debug影响正式环境
     * @param activity 发起者
     * @param info 携带的信息
     */
    public static void startDebug(Activity activity, String info){
        if(AppConfig.DEBUG){
            try {
                Intent intent = new Intent(activity, TestActivity.class);
                intent.putExtra(DEBUG_PARAM_NAME, info);
                activity.startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
