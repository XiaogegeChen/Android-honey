package com.github.xiaogegechen.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.common.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * 含有加载项的recyclerView的adapter
 * @param <VH> 子项的viewHolder
 * @param <T> 子项的数据类型
 */
public abstract class LoadMoreRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, T>
        extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder, T> {

    /**
     * 子项
     */
    private static final int TYPE_ITEM = 1;

    /**
     * 最后一项,加载页
     */
    private static final int TYPE_FOOT = 2;

    /**
     * 加载完成
     */
    public static final int STATE_DONE = 0;

    /**
     * 正在加载
     */
    public static final int STATE_LOAD = 1;

    /**
     * 加载出错
     */
    public static final int STATE_ERROR = 2;

    @IntDef({
            STATE_DONE,
            STATE_LOAD,
            STATE_ERROR
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface StateType{}

    // 当前状态
    private @StateType int mState = STATE_LOAD;

    // 数据源
    protected List<T> mList;

    // 上下文对象
    protected Context mContext;

    public LoadMoreRecyclerViewAdapter(List<T> list, Context context) {
        super (list);
        mList = list;
        mContext = context;
    }

    public void setState(@StateType int state) {
        mState = state;
        // 通知更新
        notifyItemChanged (mList.size());
    }

    @Override
    public int getItemViewType(int position) {
        if(position + 1 == getItemCount ()){
            // 最后一项是加载页面
            return TYPE_FOOT;
        }else{
            return TYPE_ITEM;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case TYPE_FOOT:
                View FootView = LayoutInflater.from (mContext)
                        .inflate (R.layout.common_load_more_recycler_view_footer, viewGroup, false);
                return new FootViewHolder (FootView);

            case TYPE_ITEM:
                View itemView = LayoutInflater.from (mContext).inflate (getItemViewLayout (),
                        viewGroup, false);
                return getItemViewHolder (itemView);
            default:
                throw new IllegalArgumentException("wrong viewType");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if(viewHolder instanceof FootViewHolder){
            FootViewHolder footHolder = (FootViewHolder) viewHolder;
            switch (mState){
                case STATE_LOAD:
                    footHolder.mProgressBar.setVisibility (View.VISIBLE);
                    footHolder.mDoneText.setVisibility (View.INVISIBLE);
                    footHolder.mErrorText.setVisibility (View.INVISIBLE);
                    break;

                case STATE_DONE:
                    footHolder.mProgressBar.setVisibility (View.INVISIBLE);
                    footHolder.mDoneText.setVisibility (View.VISIBLE);
                    footHolder.mErrorText.setVisibility (View.INVISIBLE);
                    break;

                case STATE_ERROR:
                    footHolder.mProgressBar.setVisibility (View.INVISIBLE);
                    footHolder.mDoneText.setVisibility (View.INVISIBLE);
                    footHolder.mErrorText.setVisibility (View.VISIBLE);
                    break;

                default:
                    break;
            }
        }else{
            onItemBindViewHolder (viewHolder, i);
        }
    }

    @Override
    public int getItemCount() {
        // 因为有底部,所以要加1
        return mList.size () + 1;
    }

    // 拿到子项布局的id
    public abstract int getItemViewLayout();

    // 拿到子项的viewHolder
    public abstract VH getItemViewHolder(View itemView);

    // 子项onBindViewHolder的具体实现
    public abstract void onItemBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position);

    /**
     * 加载项的viewHolder
     */
    static class FootViewHolder extends RecyclerView.ViewHolder{
        /**
         * 进度条
         */
        ProgressBar mProgressBar;

        /**
         * 加载错误
         */
        TextView mErrorText;

        /**
         * 加载完毕，不再有数据可以加载了
         */
        TextView mDoneText;

        FootViewHolder(@NonNull View itemView) {
            super (itemView);
            mProgressBar = itemView.findViewById (R.id.common_load_more_progress);
            mErrorText = itemView.findViewById (R.id.common_load_more_error);
            mDoneText = itemView.findViewById (R.id.common_load_more_done);
        }
    }
}
