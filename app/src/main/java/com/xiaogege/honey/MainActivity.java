package com.xiaogege.honey;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaogege.honey.adapter.MyFragmentPagerAdapter;
import com.xiaogege.honey.fragment.PicturesFragment;
import com.xiaogege.honey.fragment.PromissionFragment;
import com.xiaogege.honey.fragment.QQSpaceFragment;
import com.xiaogege.honey.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
    private ViewPager mViewPager;
    private TextView pictureTextView;
    private TextView qqSpaceTextView;
    private TextView promissionTextView;
    private TextView weatherTextView;
    private ImageView pictureImageView;
    private ImageView qqSpaceImageView;
    private ImageView promissionImageView;
    private ImageView weatherIamgeView;
    private List<TextView> bottomTextViewList=new ArrayList<> ();
    private List<ImageView> bottomImageViewList=new ArrayList<> ();
    private List<Fragment> mFragmentList=new ArrayList<> ();
    private static final int mDefaultColor=Color.BLACK;
    private static final int mActiveColor=Color.RED;
    private long exitTime=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        if(Build.VERSION.SDK_INT>=21){
            View decorView=getWindow ().getDecorView ();
            decorView.setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow ().setStatusBarColor (Color.WHITE);
        }
        setContentView (R.layout.activity_main);
        initView ();
        initList ();
        Log.d ("MainActivity","hello");
        //初始化在“照片墙”界面
        qqSpaceImageView.setImageResource (R.drawable.qq_space_default);
        promissionImageView.setImageResource (R.drawable.promission_default);
        weatherIamgeView.setImageResource (R.drawable.weather_default);
        pictureTextView.setTextColor (mActiveColor);
        pictureImageView.setImageResource (R.drawable.picture_active);
        mViewPager.setAdapter (new MyFragmentPagerAdapter (getSupportFragmentManager (),mFragmentList));

        mViewPager.setOnPageChangeListener (this);
        pictureImageView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                handleBottomView (0);
                mViewPager.setCurrentItem (0);
            }
        });
        qqSpaceImageView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                handleBottomView (1);
                mViewPager.setCurrentItem (1);
            }
        });
        promissionImageView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                handleBottomView (2);
                mViewPager.setCurrentItem (2);
            }
        });
        weatherIamgeView.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                handleBottomView (3);
                mViewPager.setCurrentItem (3);
            }
        });
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {}

    //页面滑动时改变图标和字体的颜色
    @Override
    public void onPageSelected(int position) {
        handleBottomView (position);
    }

    @Override
    public void onPageScrollStateChanged(int i) {}

    //实现两次点击返回键退出功能
    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis ()-exitTime>2000){
            Toast.makeText (this,"再按一次退出程序",Toast.LENGTH_SHORT).show ();
            exitTime=System.currentTimeMillis ();
        }else{
            finish ();
        }
    }

    //控件初始化
    private void initView(){
        mViewPager=findViewById (R.id.view_pager);
        pictureTextView=findViewById (R.id.picture_text);
        qqSpaceTextView=findViewById (R.id.qq_space_text);
        promissionTextView=findViewById (R.id.promission_text);
        weatherTextView=findViewById (R.id.weather_text);
        pictureImageView=findViewById (R.id.picture_image_view);
        qqSpaceImageView=findViewById (R.id.qq_space_image_view);
        promissionImageView=findViewById (R.id.promission_image_view);
        weatherIamgeView=findViewById (R.id.weather_image_view);
    }

    //底部各个控件加入相应的List里面
    private void initList(){
        bottomTextViewList.add (pictureTextView);
        bottomTextViewList.add (qqSpaceTextView);
        bottomTextViewList.add (promissionTextView);
        bottomTextViewList.add (weatherTextView);
        bottomImageViewList.add (pictureImageView);
        bottomImageViewList.add (qqSpaceImageView);
        bottomImageViewList.add (promissionImageView);
        bottomImageViewList.add (weatherIamgeView);
        mFragmentList.add (new PicturesFragment ());
        mFragmentList.add (new QQSpaceFragment ());
        mFragmentList.add (new PromissionFragment ());
        mFragmentList.add (new WeatherFragment ());
    }

    private void handleBottomView(int position){
        for(TextView textView:bottomTextViewList){
            textView.setTextColor (mDefaultColor);
        }
        bottomTextViewList.get(position).setTextColor (mActiveColor);
        switch (position){
            case 0:
                pictureImageView.setImageResource (R.drawable.picture_active);
                qqSpaceImageView.setImageResource (R.drawable.qq_space_default);
                promissionImageView.setImageResource (R.drawable.promission_default);
                weatherIamgeView.setImageResource (R.drawable.weather_default);
                break;
            case 1:
                pictureImageView.setImageResource (R.drawable.picture_default);
                qqSpaceImageView.setImageResource (R.drawable.qq_space_active);
                promissionImageView.setImageResource (R.drawable.promission_default);
                weatherIamgeView.setImageResource (R.drawable.weather_default);
                break;
            case 2:
                pictureImageView.setImageResource (R.drawable.picture_default);
                qqSpaceImageView.setImageResource (R.drawable.qq_space_default);
                promissionImageView.setImageResource (R.drawable.promission_active);
                weatherIamgeView.setImageResource (R.drawable.weather_default);
                break;
            case 3:
                pictureImageView.setImageResource (R.drawable.picture_default);
                qqSpaceImageView.setImageResource (R.drawable.qq_space_default);
                promissionImageView.setImageResource (R.drawable.promission_default);
                weatherIamgeView.setImageResource (R.drawable.weather_active);
                break;
            default:
                break;
        }
    }
}
