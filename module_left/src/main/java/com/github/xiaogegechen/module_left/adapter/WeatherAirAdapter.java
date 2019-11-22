package com.github.xiaogegechen.module_left.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.module_left.R;
import com.github.xiaogegechen.module_left.model.WeatherAir;

import java.util.List;

/**
 * {@link com.github.xiaogegechen.module_left.view.impl.WeatherDetailFragment} 显示当前空气状况的
 * recyclerView 的 adapter
 */
public class WeatherAirAdapter extends RecyclerView.Adapter<WeatherAirAdapter.ViewHolder> {

    private List<WeatherAir> mWeatherAirList;
    private Context mContext;

    public WeatherAirAdapter(List<WeatherAir> weatherAirList, Context context) {
        mWeatherAirList = weatherAirList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_left_fragment_weather_detail_air_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherAir weatherAir = mWeatherAirList.get(position);
        int textColor = mContext.getResources().getColor(weatherAir.getUIColorId());
        holder.mNameTextView.setText(weatherAir.getName());
        holder.mNameTextView.setTextColor(textColor);
        holder.mValueTextView.setText(weatherAir.getValue());
        holder.mValueTextView.setTextColor(textColor);
    }

    @Override
    public int getItemCount() {
        return mWeatherAirList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mNameTextView;
        private TextView mValueTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTextView = itemView.findViewById(R.id.module_left_fragment_weather_detail_air_item_key);
            mValueTextView = itemView.findViewById(R.id.module_left_fragment_weather_detail_air_item_value);
        }
    }

}
