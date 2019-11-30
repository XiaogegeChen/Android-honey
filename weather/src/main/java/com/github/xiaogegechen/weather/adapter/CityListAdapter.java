package com.github.xiaogegechen.weather.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.model.CityInfo;
import com.github.xiaogegechen.weather.model.event.NotifyCitySelectedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * {@link com.github.xiaogegechen.weather.view.impl.SelectCityActivity}中模糊搜索城市列表的adapter
 */
public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    // 地名层级分割符
    private static final String LOCATION_SPLIT = "，";

    private List<CityInfo> mCityInfoList;

    public CityListAdapter(List<CityInfo> cityInfoList) {
        mCityInfoList = cityInfoList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_activity_select_city_result_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            CityInfo cityInfo = mCityInfoList.get(holder.getAdapterPosition());
            EventBus.getDefault().post(new NotifyCitySelectedEvent(cityInfo, NotifyCitySelectedEvent.FLAG_FROM_FIND));
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CityInfo cityInfo = mCityInfoList.get(position);
        // 拼接字符串，格式为 "沈阳，辽宁，中国"
        String text = cityInfo.getLocation() +
                LOCATION_SPLIT +
                cityInfo.getAdminArea() +
                LOCATION_SPLIT +
                cityInfo.getCountry();
        // 填充
        holder.mTextView.setText(text);
    }

    @Override
    public int getItemCount() {
        return mCityInfoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.weather_activity_select_city_result_item_text);
        }
    }
}
