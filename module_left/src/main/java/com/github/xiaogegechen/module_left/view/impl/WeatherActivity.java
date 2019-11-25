package com.github.xiaogegechen.module_left.view.impl;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.github.xiaogegechen.common.adapter.MyFragmentPagerAdapter;
import com.github.xiaogegechen.common.base.BaseActivity;
import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.test.FiveClickHelper;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.module_left.Constants;
import com.github.xiaogegechen.module_left.R;
import com.github.xiaogegechen.module_left.model.CityInfo;
import com.github.xiaogegechen.module_left.presenter.IWeatherActivityPresenter;
import com.github.xiaogegechen.module_left.presenter.impl.WeatherActivityPresenterImpl;
import com.github.xiaogegechen.module_left.view.IWeatherActivityView;

import java.util.ArrayList;
import java.util.List;

/**
 * singleTask 模式
 */
public class WeatherActivity extends BaseActivity implements IWeatherActivityView {

    private ImageView mBackgroundImageView;
    private ImageView mAddImageView;
    private TextView mCityNameTextView;
    private TextView mRefreshTimeTextView;
    private ViewPager mViewPager;
    // viewPager相关
    private List<BaseFragment> mViewPagerDataSource;
    private MyFragmentPagerAdapter mViewPagerAdapter;

    private IWeatherActivityPresenter mWeatherActivityPresenter;

    @Override
    public void initView() {
        mBackgroundImageView = findViewById(R.id.module_left_activity_weather_background_image);
        mAddImageView = findViewById(R.id.module_left_activity_weather_add);
        mCityNameTextView = findViewById(R.id.module_left_activity_weather_city_name);
        mRefreshTimeTextView = findViewById(R.id.module_left_activity_weather_refresh_time);
        mViewPager = findViewById(R.id.module_left_activity_weather_content);

        // imm
        StatusBarUtils.setImmersive(this);
        StatusBarUtils.fillStatusBarByView(this, findViewById(R.id.module_left_activity_weather_placeholder_view));
    }

    @Override
    public void initData() {
        mWeatherActivityPresenter = new WeatherActivityPresenterImpl();
        mWeatherActivityPresenter.attach(this);
        mAddImageView.setOnClickListener(v -> mWeatherActivityPresenter.gotoManageCityActivity());
        // debug
        new FiveClickHelper()
                .fiveClick(
                        findViewById(R.id.module_left_activity_weather_debug),
                        v -> mWeatherActivityPresenter.debug()
                );
        // viewPager配置
        initViewPager();
        // 如果没有选择的城市则需要跳转到管理城市页面，有的都添加进viewPager中
        mWeatherActivityPresenter.gotoManageCityActivityIfNeeded();
    }

    private void initViewPager(){
        mViewPagerDataSource = new ArrayList<>();
        mViewPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), mViewPagerDataSource) {
            @Override
            public List<String> getTitle() {
                return null;
            }
        };
        mViewPager.setAdapter(mViewPagerAdapter);
        // 监听viewPager状态
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                WeatherDetailFragment fragment = (WeatherDetailFragment) mViewPagerDataSource.get(position);
                mCityNameTextView.setText(fragment.getCityInfo().getLocation());
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        CityInfo cityInfo = intent.getParcelableExtra(Constants.INTENT_PARAM_CITY_INFO);
        addCity2ViewPager(cityInfo);
    }

    @Override
    protected void onDestroy() {
        mWeatherActivityPresenter.detach();
        super.onDestroy();
    }

    @Override
    public void addCity2ViewPager(CityInfo cityInfo) {
        int position = addCity2ViewPagerDataSource(cityInfo);
        // 刷新数据源并定位到这个城市的页面
        mViewPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void addCityList2ViewPager(List<CityInfo> cityInfoList) {
        int lastPosition = -1;
        for (int i = 0; i < cityInfoList.size(); i++) {
            CityInfo cityInfo = cityInfoList.get(i);
            lastPosition = addCity2ViewPagerDataSource(cityInfo);
        }
        // 刷新数据源并定位到最后一个页面
        mViewPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(lastPosition);
    }

    /**
     * 添加一个城市到viewPagerDataSource中，并返回这个城市在viewPagerDataSource中的位置。如果城市已经存在则直接返回
     * 其位置，不存在则会新建一个fragment添加进viewPagerDataSource中，并返回其位置。这个方法不刷新viewpager
     *
     * @param cityInfo 城市信息
     * @return 这个城市在viewPagerDataSource中的位置
     */
    private int addCity2ViewPagerDataSource(CityInfo cityInfo){
        // 查看对应城市是不是在viewPagerDataSource中，如果是直接跳转到对应页，不是就新添加一个页面
        int position = -1;
        if(mViewPagerDataSource.size() > 0){
            for (int i = 0; i < mViewPagerDataSource.size(); i++) {
                WeatherDetailFragment fragment = (WeatherDetailFragment) mViewPagerDataSource.get(i);
                CityInfo cityInfoOfFragment = fragment.getCityInfo();
                // 只用比较id即可，id是唯一的
                if(cityInfo.getCityId().equals(cityInfoOfFragment.getCityId())){
                    position = i;
                    break;
                }
            }
            if(position == -1){
                // 说明现在没有与之匹配的城市，新加一个
                mViewPagerDataSource.add(new WeatherDetailFragment(cityInfo));
                position = mViewPagerDataSource.size() - 1;
            }
        }else{
            // 说明目前还没有加城市
            mViewPagerDataSource.add(new WeatherDetailFragment(cityInfo));
            position = 0;
        }
        return position;
    }

    @Override
    public int getLayoutId() {
        return R.layout.module_left_activity_weather;
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public void showProgress() {}

    @Override
    public void showErrorPage() {}

    @Override
    public void showToast(String message) {
        ToastUtil.show(this, message);
    }

}
