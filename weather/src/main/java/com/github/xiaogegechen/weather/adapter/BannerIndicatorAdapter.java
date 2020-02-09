package com.github.xiaogegechen.weather.adapter;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.weather.Constants;
import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.model.BannerIndicator;

import java.util.List;

/**
 * 轮播图指示器（不是轮播图自带的指示器）的adapter
 */
public class BannerIndicatorAdapter extends RecyclerView.Adapter<BannerIndicatorAdapter.ViewHolder> {
    private List<BannerIndicator> mList;
    private RecyclerView mRecyclerView;

    public BannerIndicatorAdapter(List<BannerIndicator> list) {
        mList = list;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_banner_indicator_rv_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            // 跳转到响应的activity
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BannerIndicator bannerIndicator = mList.get(position);
        ViewGroup.LayoutParams itemViewLayoutParams = holder.itemView.getLayoutParams();
        itemViewLayoutParams.width = calculateItemWidth();
        if(bannerIndicator.isSelected()){
            // 选中的是粗体
            holder.mTextView.setTypeface(null, Typeface.BOLD);
        }else{
            holder.mTextView.setTypeface(null, Typeface.NORMAL);
        }
        holder.mTextView.setText(bannerIndicator.getName());
        holder.mImageView.setImageResource(bannerIndicator.getIconId());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 计算子项宽度
     *
     * @return mSelectedCitiesRv 的子项宽度
     */
    private int calculateItemWidth(){
        int newWidth;
        int count = getItemCount();
        if(count >= Constants.BANNER_INDICATOR_RV_MAX_COUNT){
            newWidth = mRecyclerView.getWidth() / Constants.BANNER_INDICATOR_RV_MAX_COUNT;
        }else{
            newWidth = mRecyclerView.getWidth() / count;
        }
        return newWidth;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mImageView;
        TextView mTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.icon);
            mTextView = itemView.findViewById(R.id.name);
        }
    }
}
