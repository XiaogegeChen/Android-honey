package com.github.xiaogegechen.design.viewgroup;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.xiaogegechen.design.R;
import com.github.xiaogegechen.design.Utils;
import com.github.xiaogegechen.design.view.TagTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义轮播图控件
 */
public class BannerView extends FrameLayout {
    // ------------------属性默认值-----------------
    private static final int ARC_HEIGHT_DEFAULT = Utils.dp2px(20);
    private static final int INDICATOR_NORMAL_SIZE_DEFAULT = Utils.dp2px(8);
    private static final int INDICATOR_GAP_DEFAULT = Utils.dp2px(8);
    private static final int INDICATOR_NORMAL_COLOR_DEFAULT = Color.GRAY;
    private static final int INDICATOR_SELECTED_COLOR_DEFAULT = Color.YELLOW;
    private static final int ARC_REGION_COLOR_DEFAULT = Color.WHITE;
    private static final int INDICATOR_GRAVITY_DEFAULT = Gravity.CENTER;
    private static final int INTERVAL_DEFAULT = 3000;

    // ------------------组成控件的View-----------------
    private ViewPager mViewPager;
    private RecyclerView mRecyclerView;

    // recyclerView相关
    private MyRecyclerViewAdapter mRecyclerViewAdapter;
    private List<MyRvBean> mRecyclerViewDataSource;
    // 指示器配置
    private IndicatorOptions mIndicatorOptions;

    /**
     * 数据源，图片的url集合
     */
    private List<String> mDataSource;

    /**
     * 轮播图播放时间间隔
     */
    private int mInterval;

    /**
     * 轮播图点击监听器
     */
    private OnItemClickListener mOnItemClickListener;

    /**
     * 页面选中监听器
     */
    private OnItemSelectedListener mOnItemSelectedListener;

    /**
     * glide展位图
     */
    private @DrawableRes int mPlaceholderId;

    /**
     * glide加载失败图
     */
    private @DrawableRes int mErrorId;

    private Timer mTimer;
    private Configurator mConfigurator;

    public BannerView(@NonNull Context context) {
        this(context, null);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.design_banner_view, this);
        // 自定义属性
        TypedArray typedArray = context.obtainStyledAttributes (attrs, R.styleable.BannerView);
        int arcHeight = typedArray.getDimensionPixelSize(R.styleable.BannerView_bv_arc_height, ARC_HEIGHT_DEFAULT);
        int indicatorSize = typedArray.getDimensionPixelSize(R.styleable.BannerView_bv_indicator_size, INDICATOR_NORMAL_SIZE_DEFAULT);
        int indicatorGap = typedArray.getDimensionPixelSize(R.styleable.BannerView_bv_indicator_gap, INDICATOR_GAP_DEFAULT);
        int indicatorColor = typedArray.getColor(R.styleable.BannerView_bv_indicator_color, INDICATOR_NORMAL_COLOR_DEFAULT);
        int indicatorSelectedColor = typedArray.getColor(R.styleable.BannerView_bv_indicator_selected_color, INDICATOR_SELECTED_COLOR_DEFAULT);
        int rvGravity = typedArray.getInt(R.styleable.BannerView_bv_indicator_gravity, INDICATOR_GRAVITY_DEFAULT);
        int rvLeftMargin = typedArray.getDimensionPixelSize(R.styleable.BannerView_bv_indicator_margin_left, -1);
        int rvRightMargin = typedArray.getDimensionPixelSize(R.styleable.BannerView_bv_indicator_margin_right, -1);
        int rvTopMargin = typedArray.getDimensionPixelSize(R.styleable.BannerView_bv_indicator_margin_top, -1);
        int rvBottomMargin = typedArray.getDimensionPixelSize(R.styleable.BannerView_bv_indicator_margin_bottom, -1);
        int bottomColor = typedArray.getColor(R.styleable.BannerView_bv_bottom_color, ARC_REGION_COLOR_DEFAULT);
        mInterval = typedArray.getInteger(R.styleable.BannerView_bv_interval, INTERVAL_DEFAULT);
        typedArray.recycle();

        // helpView
        MyHelpView helpView = findViewById(R.id.helpView);
        helpView.setArcHeight(arcHeight);
        helpView.setColor(bottomColor);

        mViewPager = findViewById(R.id.viewPager);
        mRecyclerView = findViewById(R.id.recyclerView);
        // 设置一个背景色，不至于太突兀
        mViewPager.setBackgroundColor(bottomColor);

        mIndicatorOptions = new IndicatorOptions();
        mIndicatorOptions.mNormalSize = indicatorSize;
        mIndicatorOptions.mGap = indicatorGap;
        mIndicatorOptions.mNormalColor = indicatorColor;
        mIndicatorOptions.mSelectedColor = indicatorSelectedColor;

        // mRecyclerView
        LayoutParams layoutParams = (LayoutParams) mRecyclerView.getLayoutParams();
        layoutParams.gravity = rvGravity;
        layoutParams.leftMargin = rvLeftMargin;
        layoutParams.rightMargin = rvRightMargin;
        layoutParams.topMargin = rvTopMargin;
        layoutParams.bottomMargin = rvBottomMargin;
    }

    /**
     * 设置轮播图的相关配置，必须调用{@link #submit()}方法提交配置
     *
     * @param configurator 配置
     * @return this
     *
     * @see #submit()
     */
    public BannerView setConfigurator(Configurator configurator) {
        mConfigurator = configurator;
        return this;
    }

    /**
     * 提交配置
     */
    public void submit(){
        mDataSource = mConfigurator.mDataSource;
        mOnItemClickListener = mConfigurator.mOnItemClickListener;
        mOnItemSelectedListener = mConfigurator.mOnItemSelectedListener;
        mInterval = mConfigurator.mInterval;
        mPlaceholderId = mConfigurator.mPlaceholderId;
        mErrorId = mConfigurator.mErrorId;
        submitRv();
        submitVp();
    }

    /**
     * 跳转到指定位置
     *
     * @param position 指定的位置
     */
    public void setCurrentItem(int position){
        mViewPager.setCurrentItem(position);
    }

    private void submitRv(){
        mRecyclerViewDataSource = new ArrayList<>();
        for(int i = 0; i < mDataSource.size(); i ++){
            MyRvBean rvBean = new MyRvBean();
            rvBean.mIsSelected = false;
            mRecyclerViewDataSource.add(rvBean);
        }
        mRecyclerViewAdapter = new MyRecyclerViewAdapter(mRecyclerViewDataSource, mIndicatorOptions, mViewPager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
    }

    private void submitVp(){
        mViewPager.setAdapter(new MyPagerAdapter(mDataSource, mOnItemClickListener, mPlaceholderId, mErrorId));
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                makeItemSelected(position);
            }
        });
        mViewPager.setCurrentItem(calculateCenterIndex());
    }

    private int calculateCenterIndex(){
        int size = mDataSource.size();
        if(size == 1){
            return 0;
        }else{
            return size / 2;
        }
    }

    private void makeItemSelected(int position){
        if (mOnItemSelectedListener != null) {
            mOnItemSelectedListener.onItemSelected(position);
        }
        for (int i = 0; i < mRecyclerViewDataSource.size(); i++) {
            mRecyclerViewDataSource.get(i).mIsSelected = (i == position);
        }
        mRecyclerViewAdapter.notifyDataSetChanged();
        // 定时任务
        if(mTimer != null){
            mTimer.cancel();
        }
        mTimer = new Timer();
        TimerTask timerTask = new MyTimerTask(mViewPager);
        mTimer.schedule(timerTask, mInterval);
    }

    /**
     * 页面点击监听器，监听轮播图的点击事件
     */
    public interface OnItemClickListener{
        /**
         * 当轮播图被点击时触发
         *
         * @param itemView 被点击的view，是ImageView
         * @param position 位置
         */
        void onItemClick(View itemView, int position);
    }

    /**
     * 页面选中监听器
     */
    public interface OnItemSelectedListener{
        /**
         * 当轮播图停留在某一页面时触发
         *
         * @param position 位置
         */
        void onItemSelected(int position);
    }

    /**
     * 轮播图配置类，用于存储轮播图的配置
     */
    public static class Configurator{
        List<String> mDataSource;
        OnItemClickListener mOnItemClickListener;
        OnItemSelectedListener mOnItemSelectedListener;
        int mInterval;
        @DrawableRes int mPlaceholderId;
        @DrawableRes int mErrorId;

        /**
         * 设置数据源
         *
         * @param dataSource 数据源，url集合
         * @return this
         */
        public Configurator dataSource(List<String> dataSource){
            mDataSource = dataSource;
            return this;
        }

        /**
         * 设置点击监听
         *
         * @param onItemClickListener 轮播图点击监听器
         * @return this
         */
        public Configurator onItemClickListener(OnItemClickListener onItemClickListener){
            mOnItemClickListener = onItemClickListener;
            return this;
        }

        /**
         * 设置页面选中监听
         *
         * @param onItemSelectedListener 轮播图页面选中监听器
         * @return this
         */
        public Configurator onItemSelectedListener(OnItemSelectedListener onItemSelectedListener){
            mOnItemSelectedListener = onItemSelectedListener;
            return this;
        }

        /**
         * 轮播时间间隔
         *
         * @param interval 时间间隔
         * @return this
         */
        public Configurator interval(int interval){
            mInterval = interval;
            return this;
        }

        public Configurator placeholderId(@DrawableRes int placeholderId){
            mPlaceholderId = placeholderId;
            return this;
        }

        public Configurator errorId(@DrawableRes int errorId){
            mErrorId = errorId;
            return this;
        }
    }

    static class MyPagerAdapter extends PagerAdapter {
        List<String> mDataSource;
        OnItemClickListener mOnItemClickListener;
        @DrawableRes int mPlaceholderId;
        @DrawableRes int mErrorId;

        MyPagerAdapter(List<String> dataSource,
                       OnItemClickListener onItemClickListener,
                       @DrawableRes int placeholderId,
                       @DrawableRes int errorId) {
            mDataSource = dataSource;
            mOnItemClickListener = onItemClickListener;
            mPlaceholderId = placeholderId;
            mErrorId = errorId;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            String url = mDataSource.get(position);
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.design_pager, container, false);
            ImageView imageView = view.findViewById(R.id.image);
            Glide.with(container.getContext())
                    .asBitmap()
                    .load(url)
                    .apply(new RequestOptions().placeholder(mPlaceholderId).error(mErrorId))
                    .into(imageView);
            if (mOnItemClickListener != null) {
                imageView.setOnClickListener(v -> mOnItemClickListener.onItemClick(v, position));
            }
            container.addView(imageView);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }
    }

    static class MyTimerTask extends TimerTask{
        private ViewPager mViewPager;

        MyTimerTask(ViewPager viewPager) {
            mViewPager = viewPager;
        }

        @Override
        public void run() {
            PagerAdapter adapter = mViewPager.getAdapter();
            if (adapter != null) {
                int currentIndex = mViewPager.getCurrentItem();
                int pageCount = mViewPager.getAdapter().getCount();
                int nextIndex;
                if(currentIndex == pageCount - 1){
                    nextIndex = 0;
                }else{
                    nextIndex = currentIndex + 1;
                }
                mViewPager.post(() -> mViewPager.setCurrentItem(nextIndex));
            }
        }
    }

    static class MyRvBean{
        boolean mIsSelected;
    }

    static class IndicatorOptions{
        int mNormalSize;
        int mGap;
        @ColorInt int mNormalColor;
        @ColorInt int mSelectedColor;
    }

    static class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>{

        private List<MyRvBean> mRvBeanList;
        private IndicatorOptions mOptions;
        private ViewPager mViewPager;

        MyRecyclerViewAdapter(List<MyRvBean> rvBeanList, IndicatorOptions options, ViewPager viewPager) {
            mRvBeanList = rvBeanList;
            mOptions = options;
            mViewPager = viewPager;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.design_bv_rv_item, parent, false);
            ViewHolder holder = new ViewHolder(view, mOptions);
            holder.mNormalView.setOnClickListener(v -> {
                // 跳转到指定位置
                int position = holder.getAdapterPosition();
                mViewPager.setCurrentItem(position);
            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            MyRvBean rvBean = mRvBeanList.get(position);
            boolean selected = rvBean.mIsSelected;
            if(selected){
                holder.mSelectedView.setVisibility(VISIBLE);
                holder.mNormalView.setVisibility(INVISIBLE);
            }else{
                holder.mSelectedView.setVisibility(INVISIBLE);
                holder.mNormalView.setVisibility(VISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return mRvBeanList.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder{
            TagTextView mNormalView;
            TagTextView mSelectedView;

            ViewHolder(@NonNull View itemView, IndicatorOptions options) {
                super(itemView);
                mNormalView = itemView.findViewById(R.id.normal);
                mSelectedView = itemView.findViewById(R.id.selected);
                // 重新配置view的参数
                ViewGroup.LayoutParams normalViewLayoutParams = mNormalView.getLayoutParams();
                normalViewLayoutParams.width = options.mNormalSize;
                normalViewLayoutParams.height = options.mNormalSize;
                mNormalView.setBgColor(options.mNormalColor);
                ViewGroup.LayoutParams selectedViewLayoutParams = mSelectedView.getLayoutParams();
                selectedViewLayoutParams.width = options.mNormalSize + options.mGap;
                selectedViewLayoutParams.height = options.mNormalSize;
                mSelectedView.setBgColor(options.mSelectedColor);
            }
        }
    }

    public static class MyHelpView extends View {
        private int mColor;
        private Path mPath;
        private Paint mPaint;
        private int mArcHeight;

        public MyHelpView(Context context) {
            this(context, null);
        }

        public MyHelpView(Context context, @Nullable AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public MyHelpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            mPath = new Path();
            mPaint = new Paint();
            setClickable(false);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            mPaint.setColor(mColor);
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setAntiAlias(true);

            int height = getHeight();
            int width = getWidth();
            mPath.moveTo(0, height - mArcHeight);
            mPath.quadTo((float) (width * 1.0 / 2), height, width, height - mArcHeight);
            mPath.lineTo(width, height);
            mPath.lineTo(0, height);
            mPath.lineTo(0, height - mArcHeight);
            canvas.drawPath(mPath, mPaint);
        }

        public void setColor(int color) {
            mColor = color;
            invalidate();
        }

        public void setArcHeight(int arcHeight) {
            mArcHeight = arcHeight;
            invalidate();
        }
    }
}