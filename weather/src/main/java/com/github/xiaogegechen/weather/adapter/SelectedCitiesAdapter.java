package com.github.xiaogegechen.weather.adapter;

import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.weather.Constants;
import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.model.SelectedCityForRv;
import com.github.xiaogegechen.weather.model.event.NotifySelectedCitiesRvItemClickedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 首页显示已选城市的Rv的adapter
 */
public class SelectedCitiesAdapter extends RecyclerView.Adapter<SelectedCitiesAdapter.ViewHolder> {
    private static final String TAG = "SelectedCitiesAdapter";

    private List<SelectedCityForRv> mList;
    private RecyclerView mRecyclerView;

    public SelectedCitiesAdapter(List<SelectedCityForRv> list) {
        mList = list;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_selected_cities_rv_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            if(!mList.get(position).isSelected()){
                // viewPager定位到指定位置
                NotifySelectedCitiesRvItemClickedEvent event = new NotifySelectedCitiesRvItemClickedEvent();
                event.setItemPosition(position);
                EventBus.getDefault().post(event);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SelectedCityForRv selectedCityForRv = mList.get(position);
        int itemWidth = calculateItemWidth();
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.width = itemWidth;
        Log.d(TAG, "rvWidth -> " + mRecyclerView.getWidth() + ", itemWidth -> " + itemWidth);
        if(selectedCityForRv.isSelected()){
            holder.mTextView.setTypeface(null, Typeface.BOLD);
            holder.mIndicatorView.setVisibility(View.VISIBLE);
        }else{
            holder.mTextView.setTypeface(null,Typeface.NORMAL);
            holder.mIndicatorView.setVisibility(View.INVISIBLE);
        }
        holder.mTextView.setText(selectedCityForRv.getName());
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
        if(count >= Constants.SELECTED_CITIES_RV_MAX_COUNT){
            newWidth = mRecyclerView.getWidth() / Constants.SELECTED_CITIES_RV_MAX_COUNT;
        }else{
            newWidth = mRecyclerView.getWidth() / count;
        }
        return newWidth;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView mTextView;
        View mIndicatorView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.cityName);
            mIndicatorView = itemView.findViewById(R.id.indicator);
        }
    }
}
