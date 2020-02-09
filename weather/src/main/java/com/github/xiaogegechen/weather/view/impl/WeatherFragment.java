package com.github.xiaogegechen.weather.view.impl;

import android.graphics.Color;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Px;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.github.xiaogegechen.common.Constants;
import com.github.xiaogegechen.common.base.BaseFragment;
import com.github.xiaogegechen.common.base.EventBusFragment;
import com.github.xiaogegechen.common.event.MainActivityOnNewIntentEvent;
import com.github.xiaogegechen.common.util.ListUtils;
import com.github.xiaogegechen.common.util.LogUtil;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.design.view.BannerTextView;
import com.github.xiaogegechen.design.viewgroup.BannerView;
import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.adapter.BannerIndicatorAdapter;
import com.github.xiaogegechen.weather.adapter.SelectedCitiesAdapter;
import com.github.xiaogegechen.weather.helper.SelectedCitiesManager;
import com.github.xiaogegechen.weather.model.BannerIndicator;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.SelectedCityForRv;
import com.github.xiaogegechen.weather.model.SelectedCityForRvInMCAct;
import com.github.xiaogegechen.weather.model.db.SelectedCity;
import com.github.xiaogegechen.weather.model.event.NotifySelectedCitiesRvItemClickedEvent;
import com.github.xiaogegechen.weather.presenter.IWeatherFragmentPresenter;
import com.github.xiaogegechen.weather.presenter.impl.WeatherFragmentPresenterImpl;
import com.github.xiaogegechen.weather.view.IWeatherFragmentView;
import com.google.android.material.appbar.AppBarLayout;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static com.github.xiaogegechen.common.arouter.ARouterMap.WEATHER_FRAGMENT_WEATHER;

@Route(path = WEATHER_FRAGMENT_WEATHER)
public class WeatherFragment extends EventBusFragment implements IWeatherFragmentView {
    private static final String TAG = "WeatherFragment";
    @Px
    private static final int CRITICAL_BG_INVISIBLE = 10;

    private ImageView mBackgroundImageView;
    private TextView mSettingTextView;
    private TextView mManageTextView;
    private View mTitleGroupBg;
    private BannerView mBannerView;
    private ViewPager mViewPager;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;
    private Group mSearchGroup;

    // mBannerTextView 相关
    private BannerTextView mBannerTextView;
    private List<String> mBannerTextViewDataSource;

    // 指示轮播图的recyclerView
    private RecyclerView mBannerIndicatorRv;
    private List<BannerIndicator> mBannerIndicatorRvDataSource;
    private BannerIndicatorAdapter mBannerIndicatorRvAdapter;

    // 已选中城市的recyclerView
    private RecyclerView mSelectedCitiesRv;
    private List<SelectedCityForRv> mSelectedCitiesRvDataSource;
    private SelectedCitiesAdapter mSelectedCitiesRvAdapter;
    private LinearLayoutManager mSelectedCitiesRvLayoutManager;
    private int mSelectedCitiesRvHeight;

    // viewPager相关
    private List<BaseFragment> mViewPagerDataSource;
    private PagerAdapter mViewPagerAdapter;
    private List<String> mBannerViewDataSource;

    private IWeatherFragmentPresenter mWeatherFragmentPresenter;

    @Override
    public void initView(@NotNull View view) {
        mBackgroundImageView = view.findViewById(R.id.weather_activity_weather_background_image);
        mSettingTextView = view.findViewById(R.id.weather_activity_weather_setting);
        mManageTextView = view.findViewById(R.id.manageCity);
        ViewGroup titleGroup = view.findViewById(R.id.titleLayout);
        mTitleGroupBg = view.findViewById(R.id.titleLayoutBg);
        mBannerView = view.findViewById(R.id.bannerView);
        mViewPager = view.findViewById(R.id.weather_activity_weather_content);
        mAppBarLayout = view.findViewById(R.id.appBarLayout);
        mToolbar = view.findViewById(R.id.toolBar);
        mBannerIndicatorRv = view.findViewById(R.id.bannerRv);
        mSelectedCitiesRv = view.findViewById(R.id.selectedCityRv);
        mSearchGroup = view.findViewById(R.id.searchGroup);
        mBannerTextView = view.findViewById(R.id.hotCity);
        initSearchGroup(view);
        // imm， 状态栏黑色字
        StatusBarUtils.fillStatusBarByView(obtainActivity(), view.findViewById(R.id.weather_activity_weather_placeholder_view));
        // 头部透明，不遮挡轮播图
        titleGroup.setBackgroundColor(Color.TRANSPARENT);
        // appBarLayout背景透明，尽可能展示出背景图
        mAppBarLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    // 把 mSearchGroup 中所有的view放进 mSearchGroup 的tag中，方便之后取出设置点击加监听
    private void initSearchGroup(View view){
        int[] ids = mSearchGroup.getReferencedIds();
        View[] views = new View[ids.length];
        for (int i = 0; i < ids.length; i++) {
            views[i] = view.findViewById(ids[i]);
        }
        mSearchGroup.setTag(views);
    }

    @Override
    public void initData() {
        mWeatherFragmentPresenter = new WeatherFragmentPresenterImpl(mBackgroundImageView);
        mWeatherFragmentPresenter.attach(this);
        // mManageTextView
        mManageTextView.setOnClickListener(v -> {
            // 先从各个fragment中收集信息，再携带跳转
            List<SelectedCityForRvInMCAct> data = new ArrayList<>();
            for (BaseFragment baseFragment : mViewPagerDataSource) {
                data.add(((WeatherDetailFragment)baseFragment).getCurrWeatherInfoAboutThis());
            }
            mWeatherFragmentPresenter.gotoManageCityActivity(data);
        });
        // mSettingTextView
        mSettingTextView.setOnClickListener(v -> {
            // 进入设置界面
            mWeatherFragmentPresenter.gotoSetting();
        });
        // mSearchGroup
        View[] views = (View[]) mSearchGroup.getTag();
        for (View view : views) {
            view.setOnClickListener(v -> {
                // 跳转到选择城市界面
                mWeatherFragmentPresenter.gotoSelectedCityActivity();
            });
        }
        // mBannerTextView
        initBannerTextView();
        // bannerView
        initBannerView();
        // mBannerIndicatorRv
        initBannerIndicatorRv();
        // mSelectedCitiesRv
        initSelectedCitiesRv();
        // viewPager配置
        initViewPager();
        // mAppBarLayout
        mAppBarLayout.addOnOffsetChangedListener((appBarLayout, i) -> {
            // 监听appbarLayout的滑动，改变头部透明度
            int max = appBarLayout.getTotalScrollRange();
            int offset = -1 * i;
            float rate = (float) (offset * 1.0 / max);
            mTitleGroupBg.setAlpha(rate);
            if(Math.abs(max - offset) < CRITICAL_BG_INVISIBLE){
                // 背景图可见
                mBackgroundImageView.setVisibility(View.VISIBLE);
            }else{
                mBackgroundImageView.setVisibility(View.INVISIBLE);
            }
        });
        // 加载热门城市
        mWeatherFragmentPresenter.queryHotCity();
        // 加载轮播图
        mWeatherFragmentPresenter.queryBannerViewContent();
        // 加载背景图
        mWeatherFragmentPresenter.queryDayPic();
        // 如果没有选择的城市则需要跳转到管理城市页面，有的都添加进viewPager中
        mWeatherFragmentPresenter.gotoManageCityActivityIfNeeded();
    }

    @Override
    public void onResume() {
        mBannerTextView.start();
        super.onResume();
    }

    @Override
    public void onPause() {
        mBannerTextView.stop();
        super.onPause();
    }

    private void initBannerTextView(){
        mBannerTextView.setInterval(com.github.xiaogegechen.weather.Constants.BANNER_TEXT_VIEW_INTERVAL);
        mBannerTextViewDataSource = new ArrayList<>();
        mBannerTextViewDataSource.add(getString(R.string.weather_click_to_search));
        mBannerTextView.setDataSource(mBannerTextViewDataSource);
//        mBannerTextView.start();
    }

    /**
     * 初始化配置 mBannerView
     */
    private void initBannerView(){
        mBannerViewDataSource = new ArrayList<>();
        // mBannerIndicatorRv 更新item状态
        BannerView.Configurator bannerViewConfigurator = new BannerView.Configurator()
                .dataSource(mBannerViewDataSource)
                .onItemClickListener((itemView, position) -> {
                    // TODO 点击条找
                })
                .onItemSelectedListener(position -> {
                    // mBannerIndicatorRv 更新item状态
                    for (int i = 0; i < mBannerIndicatorRvDataSource.size(); i++) {
                        BannerIndicator bannerIndicator = mBannerIndicatorRvDataSource.get(i);
                        bannerIndicator.setSelected(position == i);
                    }
                    mBannerIndicatorRvAdapter.notifyDataSetChanged();
                })
                .placeholderId(R.drawable.design_loading)
                .interval(com.github.xiaogegechen.weather.Constants.BANNER_INTERVAL);
        mBannerView.setConfigurator(bannerViewConfigurator).submit();
    }

    /**
     * 初始化配置 mBannerIndicatorRv
     */
    private void initBannerIndicatorRv(){
        mBannerIndicatorRvDataSource = new ArrayList<>();
        mBannerIndicatorRvAdapter = new BannerIndicatorAdapter(mBannerIndicatorRvDataSource);
        mBannerIndicatorRv.setLayoutManager(new LinearLayoutManager(obtainActivity(), LinearLayoutManager.HORIZONTAL, false));
        mBannerIndicatorRv.setAdapter(mBannerIndicatorRvAdapter);
    }

    /**
     * 初始化配置 mSelectedCitiesRv
     */
    private void initSelectedCitiesRv(){
        mSelectedCitiesRvDataSource = new ArrayList<>();
        mSelectedCitiesRvAdapter = new SelectedCitiesAdapter(mSelectedCitiesRvDataSource);
        mSelectedCitiesRvLayoutManager = new LinearLayoutManager(obtainActivity(), LinearLayoutManager.HORIZONTAL, false);
        mSelectedCitiesRv.setLayoutManager(mSelectedCitiesRvLayoutManager);
        mSelectedCitiesRv.setAdapter(mSelectedCitiesRvAdapter);
        // layout结束刷新一下 mSelectedCitiesRv,因为这个时候才能拿到真实的宽度
        mSelectedCitiesRv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(mSelectedCitiesRv.getWidth() != 0){
                    mSelectedCitiesRv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    mSelectedCitiesRvAdapter.notifyDataSetChanged();
                }
            }
        });
        // layout结束确定高度后更新 mToolBar 的配置，使 mSelectedCitiesRv 刚好固定在最上边
        mSelectedCitiesRv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int lastHeight = mSelectedCitiesRvHeight;
                mSelectedCitiesRvHeight = mSelectedCitiesRv.getHeight();
                if(lastHeight == mSelectedCitiesRvHeight){
                    mSelectedCitiesRv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    ViewGroup.LayoutParams layoutParams = mToolbar.getLayoutParams();
                    layoutParams.height = mSelectedCitiesRvHeight;
                    if(layoutParams instanceof ViewGroup.MarginLayoutParams){
                        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + StatusBarUtils.getHeight(WeatherFragment.this.obtainContext());
                    }

                }
            }
        });
    }

    /**
     * 初始化配置 mViewPager
     */
    private void initViewPager(){
        mViewPagerDataSource = new ArrayList<>();
        mViewPagerAdapter = new MyPagerAdapter(getFragmentManager(), mViewPagerDataSource);
        mViewPager.setAdapter(mViewPagerAdapter);
        // 监听viewPager状态
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                // 更新 mSelectedCitiesRv
                int lastPos = -1;
                for (int i = 0; i < mSelectedCitiesRvDataSource.size(); i++) {
                    SelectedCityForRv rvData = mSelectedCitiesRvDataSource.get(i);
                    if(rvData.isSelected()){
                        lastPos = i;
                        rvData.setSelected(false);
                    }
                }
                mSelectedCitiesRvDataSource.get(position).setSelected(true);
                mSelectedCitiesRvAdapter.notifyItemChanged(position);
                if(lastPos != -1){
                    mSelectedCitiesRvAdapter.notifyItemChanged(lastPos);
                }
                selectedCitiesRvScrollToPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    public boolean isStatusBarTextDark() {
        return true;
    }

    @Override
    public void onDestroy() {
        mWeatherFragmentPresenter.detach();
        super.onDestroy();
    }

    /**
     * 接收到主界面转发的事件，这个事件来自于{@link SelectCityActivity}或者{@link ManageCityActivity}.通知当前
     * 的fragment用户选择的城市发生了变化，要更新viewPager
     *
     * @param event event
     */
    @Subscribe
    public void onHandleMainActivityOnNewIntentEvent(MainActivityOnNewIntentEvent event){
        // 先确定是自己需要的事件
        String from = event.getFrom();
        boolean needed = Constants.INTENT_PARAM_FROM_SELECT_CITY_ACTIVITY.equals(from)
                || Constants.INTENT_PARAM_FROM_MANAGE_CITY_ACTIVITY.equals(from);
        if(needed){
            // 重新查询数据库，和当前viewPager内容对比并更新viewPager
            List<SelectedCity> newestSelectedCityList = SelectedCitiesManager.getInstance().getSelectedCities();

//            // 找到viewPager中有但是数据库中没有的城市，从viewPagerDataSource中移除
//            int[] onlyInVpPositionArr = ListUtils.positionsOfOnlyInFirstElement(convertVpDataSourceToOnlyHasCityIdSelectedCityList(), newestSelectedCityList);
//            // 统一移除，没变化则不需要执行
//            if(onlyInVpPositionArr.length > 0){
//                for (int i = 0; i < onlyInVpPositionArr.length; i++) {
//                    int position = onlyInVpPositionArr[i] - i;
//                    // vp和rv都移除
//                    mViewPagerDataSource.remove(position);
//                    mSelectedCitiesRvDataSource.remove(position);
//                }
//            }
//            // 找到数据库中有但是viewPager中没有的城市，添加进viewPagerDataSource中
//            List<SelectedCity> onlyInDb = ListUtils.onlyInFirst(newestSelectedCityList, convertVpDataSourceToOnlyHasCityIdSelectedCityList());
//            if(onlyInDb.size() > 0){
//                for (SelectedCity shouldAddedCity : onlyInDb) {
//                    // 这个 shouldAddedCity 中有这个city的全部信息，来自数据库
//                    // vp和rv都添加
//                    BaseFragment fragment = new WeatherDetailFragment(shouldAddedCity.convert2CityInfo());
//                    mViewPagerDataSource.add(fragment);
//                    SelectedCityForRv sc = new SelectedCityForRv();
//                    sc.setName(shouldAddedCity.getLocation());
//                    sc.setSelected(false);
//                    mSelectedCitiesRvDataSource.add(sc);
//                }
//            }
//
//            mSelectedCitiesRvAdapter.notifyDataSetChanged();
//            mViewPagerAdapter.notifyDataSetChanged();

            mViewPagerDataSource.clear();
            mSelectedCitiesRvDataSource.clear();
            for (int i = 0; i < newestSelectedCityList.size(); i++) {
                SelectedCity selectedCity = newestSelectedCityList.get(i);
                BaseFragment fragment = new WeatherDetailFragment(selectedCity.convert2CityInfo());
                mViewPagerDataSource.add(fragment);
                SelectedCityForRv sc = new SelectedCityForRv();
                sc.setName(selectedCity.getLocation());
                sc.setSelected(false);
                mSelectedCitiesRvDataSource.add(sc);
            }
            mViewPagerAdapter.notifyDataSetChanged();
            mSelectedCitiesRvAdapter.notifyDataSetChanged();


        }
        // 从 ManageCityActivity来的事件有可能携带一个城市，表示这个城市被点击，跳转到这个城市的那一页
        if(Constants.INTENT_PARAM_FROM_MANAGE_CITY_ACTIVITY.equals(from)){
            LogUtil.d(TAG, "handle intent from manage city activity");
            Parcelable[] dataArray = event.getData();
            if(dataArray.length > 0){
                Parcelable parcelable = dataArray[0];
                // viewPager定位到这一页
                if(parcelable instanceof CityInfo){
                    String cityId = ((CityInfo) (dataArray[0])).getCityId();
                    LogUtil.d(TAG, "onNewIntent from manage city activity, cityId -> " + cityId);
                    // viewPager定位到这一页
                    for (int i = 0; i < mViewPagerDataSource.size(); i++) {
                        if(((WeatherDetailFragment)(mViewPagerDataSource.get(i))).getCityInfo().getCityId().equals(cityId)){
                            mViewPager.setCurrentItem(i, false);
                        }
                    }
                }
            }
        }
    }

    private List<SelectedCity> convertVpDataSourceToOnlyHasCityIdSelectedCityList(){
        List<SelectedCity> onlyHasCityIdSelectedCityList = new ArrayList<>(mViewPagerDataSource.size());
        for (int i = 0; i < mViewPagerDataSource.size(); i++) {
            SelectedCity onlyHasCityIdSelectedCity = new SelectedCity();
            onlyHasCityIdSelectedCity.setCityId(((WeatherDetailFragment)(mViewPagerDataSource.get(i))).getCityInfo().getCityId());
            onlyHasCityIdSelectedCityList.add(onlyHasCityIdSelectedCity);
        }
        return onlyHasCityIdSelectedCityList;
    }

    @Subscribe
    public void onHandleNotifySelectedCitiesRvItemClickedEvent(NotifySelectedCitiesRvItemClickedEvent event){
        int position = event.getItemPosition();
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void addCity2ViewPager(CityInfo cityInfo) {
        int position = addCity2ViewPagerDataSource(cityInfo);
        // 刷新数据源并定位到这个城市的页面
        mViewPagerAdapter.notifyDataSetChanged();
        mViewPager.setCurrentItem(position);
        selectedCitiesRvScrollToPosition(position);
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
        selectedCitiesRvScrollToPosition(lastPosition);
    }

    @Override
    public void addPicToBannerView(String dataSource, int dataType) {
        mBannerViewDataSource.add(dataSource);
        mBannerView.submit();
        // 同时要更新指示器 mBannerIndicatorRv
        BannerIndicator bannerIndicator = new BannerIndicator();
        switch (dataType){
            case IWeatherFragmentView.TYPE_PRECIPITATION:
                bannerIndicator.setName(getString(R.string.weather_precipitation));
                bannerIndicator.setIconId(R.drawable.weather_ic_precipitation);
                bannerIndicator.setSelected(false);
                break;
            case IWeatherFragmentView.TYPE_TEMPERATURE:
                bannerIndicator.setName(getString(R.string.weather_temperature));
                bannerIndicator.setIconId(R.drawable.weather_ic_temperature);
                bannerIndicator.setSelected(false);
                break;
            case IWeatherFragmentView.TYPE_VISIBILITY:
                bannerIndicator.setName(getString(R.string.weather_visibility));
                bannerIndicator.setIconId(R.drawable.weather_ic_visibility);
                bannerIndicator.setSelected(false);
                break;
            case IWeatherFragmentView.TYPE_WIND:
                bannerIndicator.setName(getString(R.string.weather_wind));
                bannerIndicator.setIconId(R.drawable.weather_ic_wind);
                bannerIndicator.setSelected(false);
                break;
            case IWeatherFragmentView.TYPE_SATELLITE:
                bannerIndicator.setName(getString(R.string.weather_satellite));
                bannerIndicator.setIconId(R.drawable.weather_ic_satellite);
                bannerIndicator.setSelected(false);
                break;
        }
        // 同时更改其它已经添加的item的width
        mBannerIndicatorRvDataSource.add(bannerIndicator);
        mBannerIndicatorRvAdapter.notifyDataSetChanged();
    }

    @Override
    public void showHotCities(List<String> hotCities) {
        mBannerTextViewDataSource.clear();
        mBannerTextViewDataSource.addAll(hotCities);
        mBannerTextView.start();
    }

    /**
     * 添加一个城市到viewPagerDataSource中，并返回这个城市在viewPagerDataSource中的位置。如果城市已经存在则直接返回
     * 其位置，不存在则会新建一个fragment添加进viewPagerDataSource中，并返回其位置。这个方法不刷新viewpager。同时将
     * 城市添加到 mSelectedCitiesRv 中，并刷新 mSelectedCitiesRv。
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
                // 说明现在没有与之匹配的城市，新加一个到ViewPager数据源中，同时添加到 mSelectedCitiesRv 数据源中
                // 并刷新 mSelectedCitiesRv
                mViewPagerDataSource.add(new WeatherDetailFragment(cityInfo));
                addCity2SelectedCitiesRvDataSourceAndRefreshRv(cityInfo);
                position = mViewPagerDataSource.size() - 1;
            }
        }else{
            // 说明目前还没有加城市
            mViewPagerDataSource.add(new WeatherDetailFragment(cityInfo));
            addCity2SelectedCitiesRvDataSourceAndRefreshRv(cityInfo);
            position = 0;
        }
        return position;
    }

    /**
     * 添加一个城市到 mSelectedCitiesRv 中并刷新 mSelectedCitiesRv
     *
     * @param cityInfo 指定的城市
     */
    private void addCity2SelectedCitiesRvDataSourceAndRefreshRv(CityInfo cityInfo){
        SelectedCityForRv selectedCityForRv = new SelectedCityForRv();
        selectedCityForRv.setName(cityInfo.getLocation());
        selectedCityForRv.setSelected(false);
        mSelectedCitiesRvDataSource.add(selectedCityForRv);
        mSelectedCitiesRvAdapter.notifyDataSetChanged();
    }

    /**
     * mSelectedCitiesRv 定位到指定位置
     *
     * @param position 指定位置
     */
    private void selectedCitiesRvScrollToPosition(int position){
        mSelectedCitiesRvLayoutManager.scrollToPosition(position);
    }

    @Override
    public int getLayoutId() {
        return R.layout.weather_fragment_weather;
    }

    @Override
    public void showProgress() {}

    @Override
    public void showErrorPage() {}

    @Override
    public void showToast(String message) {
        ToastUtil.show(message);
    }

    private static class MyPagerAdapter extends FragmentStatePagerAdapter {
        private List<BaseFragment> mFragmentList;

        MyPagerAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
            super(fm);
            mFragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList == null ? null : mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList == null ? 0 : mFragmentList.size();
        }

        @Override
        public void destroyItem(@NotNull ViewGroup container, int position, @NotNull Object object) {
            super.destroyItem(container, position, object);
            container.removeView(((BaseFragment)object).getView());
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}
