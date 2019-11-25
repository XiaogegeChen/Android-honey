package com.github.xiaogegechen.module_left.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.xiaogegechen.common.util.ImageParam;
import com.github.xiaogegechen.common.util.ImageUtil;
import com.github.xiaogegechen.module_left.Constants;
import com.github.xiaogegechen.module_left.R;
import com.github.xiaogegechen.module_left.model.WeatherHourly;

import java.util.List;

public class WeatherHourlyAdapter extends RecyclerView.Adapter<WeatherHourlyAdapter.ViewHolder> {

    private List<WeatherHourly> mWeatherHourlyList;
    private Context mContext;

    public WeatherHourlyAdapter(List<WeatherHourly> weatherHourlyList, Context context) {
        mWeatherHourlyList = weatherHourlyList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.module_left_fragment_weather_detail_hourly_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            // TODO 点击进入详情页，因为有很多字段没有展示
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherHourly weatherHourly = mWeatherHourlyList.get(position);
        holder.mTempTextView.setText(weatherHourly.getTemp());
        holder.mDescriptionTextView.setText(weatherHourly.getDescription());
        holder.mTimeTextView.setText(weatherHourly.getTime());
        ImageParam param = new ImageParam.Builder()
                .context(mContext)
                .imageView(holder.mImageView)
                .url(Constants.WEATHER_ICON_URL + weatherHourly.getCode() + ".png")
                .error(mContext.getResources().getDrawable(R.drawable.module_left_weather_na))
                .placeholder(mContext.getResources().getDrawable(R.drawable.module_left_weather_na))
                .build();
        ImageUtil.INSTANCE.displayImage(param);
    }

    @Override
    public int getItemCount() {
        return mWeatherHourlyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mTempTextView;
        private TextView mDescriptionTextView;
        private TextView mTimeTextView;
        private ImageView mImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTempTextView = itemView.findViewById(R.id.module_left_fragment_weather_detail_hourly_item_temp);
            mDescriptionTextView = itemView.findViewById(R.id.module_left_fragment_weather_detail_hourly_item_desc);
            mTimeTextView = itemView.findViewById(R.id.module_left_fragment_weather_detail_hourly_item_time);
            mImageView = itemView.findViewById(R.id.module_left_fragment_weather_detail_hourly_item_icon);
        }
    }
}
