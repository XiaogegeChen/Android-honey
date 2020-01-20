package com.github.xiaogegechen.weather.view.impl;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.xiaogegechen.common.Constants;
import com.github.xiaogegechen.common.adapter.MyFragmentPagerAdapter;
import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.base.EventBusFragment;
import com.github.xiaogegechen.common.event.MainActivityOnNewIntentEvent;
import com.github.xiaogegechen.common.test.FiveClickHelper;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.event.NotifyApplySettingEvent;
import com.github.xiaogegechen.weather.presenter.IWeatherFragmentPresenter;
import com.github.xiaogegechen.weather.presenter.impl.WeatherFragmentPresenterImpl;
import com.github.xiaogegechen.weather.view.IWeatherFragmentView;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.github.xiaogegechen.common.arouter.ARouterMap.WEATHER_FRAGMENT_WEATHER;

@Route(path = WEATHER_FRAGMENT_WEATHER)
public class WeatherFragment extends EventBusFragment implements IWeatherFragmentView {
    private ImageView mBackgroundImageView;
    private ImageView mAddImageView;
    private TextView mCityNameTextView;
    private TextView mSettingTextView;
    private ViewPager mViewPager;
    private View mDebugView;
    // viewPager相关
    private List<BaseFragment> mViewPagerDataSource;
    private MyFragmentPagerAdapter mViewPagerAdapter;

    private IWeatherFragmentPresenter mWeatherFragmentPresenter;

    @Override
    public void initView(@NotNull View view) {
        mBackgroundImageView = view.findViewById(R.id.weather_activity_weather_background_image);
        mAddImageView = view.findViewById(R.id.weather_activity_weather_add);
        mCityNameTextView = view.findViewById(R.id.weather_activity_weather_city_name);
        mSettingTextView = view.findViewById(R.id.weather_activity_weather_setting);
        mViewPager = view.findViewById(R.id.weather_activity_weather_content);
        mDebugView = view.findViewById(R.id.weather_activity_weather_debug);
        // imm
        StatusBarUtils.fillStatusBarByView(obtainActivity(), view.findViewById(R.id.weather_activity_weather_placeholder_view));
    }

    @Override
    public void initData() {
        mWeatherFragmentPresenter = new WeatherFragmentPresenterImpl(mBackgroundImageView);
        mWeatherFragmentPresenter.attach(this);
        // mAddImageView
        mAddImageView.setOnClickListener(v -> mWeatherFragmentPresenter.gotoManageCityActivity());
        // mSettingTextView
        mSettingTextView.setOnClickListener(v -> {
            // 进入设置界面
            mWeatherFragmentPresenter.gotoSetting();
        });
        // debug
        new FiveClickHelper()
                .fiveClick(
                        mDebugView,
                        v -> mWeatherFragmentPresenter.debug()
                );
        // viewPager配置
        initViewPager();
        // 加载背景图
        mWeatherFragmentPresenter.queryDayPic();
        // 如果没有选择的城市则需要跳转到管理城市页面，有的都添加进viewPager中
        mWeatherFragmentPresenter.gotoManageCityActivityIfNeeded();
    }

    private void initViewPager(){
        mViewPagerDataSource = new ArrayList<>();
        mViewPagerAdapter = new MyFragmentPagerAdapter(getFragmentManager(), mViewPagerDataSource) {
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
    public void onDestroy() {
        mWeatherFragmentPresenter.detach();
        super.onDestroy();
    }

    /**
     * 接收到主界面转发的事件，这个事件来自于{@link SelectCityActivity}，通知当前的fragment为viewPager增加一个
     * 界面
     *
     * @param event event
     */
    @Subscribe
    public void onHandleMainActivityOnNewIntentEvent(MainActivityOnNewIntentEvent event){
        // 先确定是自己需要的事件
        if(Constants.INTENT_PARAM_FROM_SELECT_CITY_ACTIVITY.equals(event.getFrom())){
            CityInfo cityInfo = (CityInfo) event.getData()[0];
            addCity2ViewPager(cityInfo);
        }
    }

    @Subscribe
    public void onHandleNotifyApplySettingEvent(NotifyApplySettingEvent event){
        boolean allowBgChange = event.isAllowBgChange();
        mWeatherFragmentPresenter.isAllowBgChange(allowBgChange);
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
        return R.layout.weather_fragment_weather;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showToast(String message) {
        ToastUtil.show(message);
    }
}
