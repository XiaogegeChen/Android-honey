package com.github.xiaogegechen.common.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * RecyclerViewAdapter的简单封装
 * @param <VH> ViewHolder
 * @param <T> 数据源单个数据的类型
 */
public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, T>
        extends RecyclerView.Adapter<VH> {

    // 数据源
    protected List<T> mList;

    // 滑动监听器
    private OnScrollingListener mListener;

    // RecyclerView实例
    protected RecyclerView mRecyclerView;

    public BaseRecyclerViewAdapter(List<T> list){
        mList = list;
    }

    public void setListener(OnScrollingListener listener) {
        mListener = listener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mRecyclerView.addOnScrollListener (new MyOnScrollListener ());
    }

    /**
     * 从recyclerView的尾部开始增加一些数据，并定位到新数据第一个
     * @param list 要添加的数据
     */
    public void addToEndAndScroll(List<T> list){
        if(list != null && list.size () > 0){
            mList.addAll (list);
            notifyItemRangeInserted (mList.size () - list.size (), list.size ());
            // 定位到新数据的第一个
            if(mRecyclerView != null){
                mRecyclerView.scrollToPosition (mList.size () - list.size ());
            }
        }
    }

    /**
     * 从recyclerView的尾部开始增加一些数据，不滑动
     * @param list 要添加的数据
     */
    public void addToEnd(List<T> list){
        if(list != null && list.size () > 0){
            mList.addAll (list);
            notifyItemRangeInserted (mList.size () - list.size (), list.size ());
        }
    }

    /**
     * 在recyclerView的头部开始增加一些数据
     * @param list 要添加的数据
     */
    public void addToBegin(List<T> list){
        if(list != null && list.size () > 0){
            mList.addAll (0, list);
            notifyItemRangeInserted (0, list.size ());
            //定位到新数据的第一个
            if(mRecyclerView != null){
                mRecyclerView.scrollToPosition (0);
            }
        }
    }

    private class MyOnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            if (mListener != null) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!recyclerView.canScrollVertically(1)) {
                        //是否能向下滚动，false表示已经滚动到底部
                        mListener.onScrollDown(recyclerView);
                    }else if (!recyclerView.canScrollVertically(-1)) {
                        //是否能向上滚动，false表示已经滚动到顶部
                        mListener.onScrollTop(recyclerView);
                    }
                }else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    //正在滚动中
                    mListener.onScrolling(recyclerView);
                }
            }
        }
    }

    /**
     * RecyclerView的滑动监听
     */
    public interface OnScrollingListener{
        /**
         * 正在滑动
         */
        void onScrolling(RecyclerView recyclerView);

        /**
         * 滑动到顶部
         */
        void onScrollTop(RecyclerView recyclerView);

        /**
         * 滑动到底部
         */
        void onScrollDown(RecyclerView recyclerView);
    }
}
