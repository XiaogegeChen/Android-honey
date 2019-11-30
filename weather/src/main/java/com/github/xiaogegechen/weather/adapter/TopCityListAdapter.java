package com.github.xiaogegechen.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.design.view.TagTextView;
import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.event.NotifyCityRemovedEvent;
import com.github.xiaogegechen.weather.model.event.NotifyCitySelectedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * {@link com.github.xiaogegechen.weather.view.impl.SelectCityActivity}中热门城市列表的adapter
 */
public class TopCityListAdapter extends RecyclerView.Adapter<TopCityListAdapter.ViewHolder> {

    private List<CityInfo> mCityInfoList;

    // 选中状态下的标签背景色
    private int mSelectedBackgroundColor;
    // 没有选中状态下的标签背景色
    private int mNormalBackgroundColor;

    public TopCityListAdapter(List<CityInfo> cityInfoList, Context context) {
        mCityInfoList = cityInfoList;

        mSelectedBackgroundColor = context.getResources().getColor(R.color.weather_color_primary);
        mNormalBackgroundColor = context.getResources().getColor(R.color.design_color_grey);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_activity_select_city_top_city_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        final TagTextView tagTextView = holder.mTagTextView;
        tagTextView.setOnClickListener(v -> {
            int position = holder.getAdapterPosition();
            final CityInfo cityInfo = mCityInfoList.get(position);
            if(cityInfo.isSelected()){
                // 如果已经选中，取消选中，并发送通知
                cityInfo.setSelected(false);
                tagTextView.setBgColor(mNormalBackgroundColor);
                EventBus.getDefault().post(new NotifyCityRemovedEvent(cityInfo, NotifyCityRemovedEvent.FLAG_FROM_SELECT_CITY_ACTIVITY));
            }else {
                // 如果没有选中，选中，并发送通知
                cityInfo.setSelected(true);
                tagTextView.setBgColor(mSelectedBackgroundColor);
                EventBus.getDefault().post(new NotifyCitySelectedEvent(cityInfo, NotifyCitySelectedEvent.FLAG_FROM_TOP));
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CityInfo cityInfo = mCityInfoList.get(position);
        boolean selected = cityInfo.isSelected();
        TagTextView tagTextView = holder.mTagTextView;
        tagTextView.setText(cityInfo.getLocation());
        if(selected){
            tagTextView.setBgColor(mSelectedBackgroundColor);
        }else{
            tagTextView.setBgColor(mNormalBackgroundColor);
        }
    }

    @Override
    public int getItemCount() {
        return mCityInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TagTextView mTagTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTagTextView  = itemView.findViewById(R.id.weather_activity_select_city_top_city_item_text);
        }
    }

}
