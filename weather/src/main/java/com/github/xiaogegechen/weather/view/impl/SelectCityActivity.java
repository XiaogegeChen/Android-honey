package com.github.xiaogegechen.weather.view.impl;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.github.xiaogegechen.common.Constants;
import com.github.xiaogegechen.common.arouter.ARouterMap;
import com.github.xiaogegechen.common.base.EventBusActivity;
import com.github.xiaogegechen.common.util.StatusBarUtils;
import com.github.xiaogegechen.common.util.ToastUtil;
import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.adapter.CityListAdapter;
import com.github.xiaogegechen.weather.adapter.TopCityListAdapter;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.event.NotifyCityRemovedEvent;
import com.github.xiaogegechen.weather.model.event.NotifyCitySelectedEvent;
import com.github.xiaogegechen.weather.presenter.ISelectCityActivityPresenter;
import com.github.xiaogegechen.weather.presenter.impl.SelectCityActivityPresenterImpl;
import com.github.xiaogegechen.weather.view.ISelectCityActivityView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class SelectCityActivity extends EventBusActivity implements ISelectCityActivityView {

    private ImageView mBackImageView;
    private ImageView mSearchImageView;
    private EditText mEditText;
    private RecyclerView mTopCityListRecyclerView;
    private RecyclerView mCityResultListRecyclerView;
    private Group mTopCityViewGroup;

    // mTopCityListRecyclerView
    private TopCityListAdapter mTopCityListAdapter;
    private List<CityInfo> mTopCityListDataSource;

    // mCityResultListRecyclerView
    private CityListAdapter mCityListAdapter;
    private List<CityInfo> mCityListDataSource;

    private ISelectCityActivityPresenter mSelectCityActivityPresenter;

    @Override
    public void initView() {
        mBackImageView = findViewById(R.id.weather_activity_select_city_title_back_icon);
        mSearchImageView = findViewById(R.id.weather_activity_select_city_search_icon);
        mEditText = findViewById(R.id.weather_activity_select_city_search_input);
        mTopCityListRecyclerView = findViewById(R.id.weather_activity_select_city_top_city_list);
        mTopCityViewGroup = findViewById(R.id.weather_activity_select_city_top_city_group);
        mCityResultListRecyclerView = findViewById(R.id.weather_activity_select_city_result_list);
        // imm
        StatusBarUtils.setImmersive(this);
        StatusBarUtils.fillStatusBarByView(this, findViewById(R.id.weather_activity_select_city_placeholder_view));
    }

    @Override
    public void initData() {
        mSelectCityActivityPresenter = new SelectCityActivityPresenterImpl();
        mSelectCityActivityPresenter.attach(this);
        // 返回icon
        mBackImageView.setOnClickListener(v -> finish());
        // editText
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                mSelectCityActivityPresenter.handleInput(s.toString());
            }
        });
        // 监听软键盘的搜索键
        mEditText.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                mSelectCityActivityPresenter.queryCityList(mEditText.getText().toString());
            }
            return true;
        });
        // 搜素按钮点击监听
        mSearchImageView.setOnClickListener(v -> mSelectCityActivityPresenter.queryCityList(mEditText.getText().toString()));
        // mTopCityListRecyclerView 初始化
        mTopCityListDataSource = new ArrayList<>();
        mTopCityListAdapter = new TopCityListAdapter(mTopCityListDataSource, this);
        mTopCityListRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        mTopCityListRecyclerView.setAdapter(mTopCityListAdapter);
        // mCityResultListRecyclerView 初始化
        mCityListDataSource = new ArrayList<>();
        mCityListAdapter = new CityListAdapter(mCityListDataSource);
        mCityResultListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCityResultListRecyclerView.setAdapter(mCityListAdapter);
        mCityResultListRecyclerView.setVisibility(View.GONE);
        // 请求热门城市列表
        mSelectCityActivityPresenter.queryTopCityList();
    }

    @Override
    protected void onDestroy() {
        mSelectCityActivityPresenter.finish();
        mSelectCityActivityPresenter.detach();
        super.onDestroy();
    }

    @Override
    public int getLayoutId() {
        return R.layout.weather_activity_select_city;
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

    @Subscribe
    public void onHandleCityRemovedEvent(NotifyCityRemovedEvent event){
        CityInfo cityInfo = event.getCityInfo();
        mSelectCityActivityPresenter.removeCity(cityInfo);
    }

    @Subscribe
    public void onHandleCitySelectedEvent(NotifyCitySelectedEvent event){
        int flag = event.getFlag();
        CityInfo cityInfo = event.getCityInfo();
        // 添加到已选择城市的列表中
        mSelectCityActivityPresenter.addCity(cityInfo);
        // 如果是模糊搜索的结果，直接跳转到 weatherActivity
        if (flag == NotifyCitySelectedEvent.FLAG_FROM_FIND) {
            // 重新回到主界面，携带所选择的城市信息，这个消息会经过MainActivity转发之后通过eventBus通知
            // WeatherFragment
            ARouter.getInstance()
                    .build(ARouterMap.MAIN_MAIN_ACTIVITY)
                    .withParcelableArray(Constants.INTENT_PARAM_FROM_SELECT_CITY_ACTIVITY, new CityInfo[]{cityInfo})
                    .navigation();
        }
    }

    @Override
    public void showTopCityList(List<CityInfo> cityInfoList) {
        showTopCityList();
        mTopCityListDataSource.clear();
        mTopCityListDataSource.addAll(cityInfoList);
        mTopCityListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTopCityList() {
        mCityResultListRecyclerView.setVisibility(View.GONE);
        mTopCityViewGroup.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCityList(List<CityInfo> cityInfoList) {
        showCityList();
        mCityListDataSource.clear();
        mCityListDataSource.addAll(cityInfoList);
        mCityListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showCityList() {
        mTopCityViewGroup.setVisibility(View.GONE);
        mCityResultListRecyclerView.setVisibility(View.VISIBLE);
    }
}
