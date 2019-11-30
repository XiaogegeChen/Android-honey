package com.github.xiaogegechen.weather.adapter;

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
import com.github.xiaogegechen.weather.Constants;
import com.github.xiaogegechen.weather.R;
import com.github.xiaogegechen.weather.model.Hourly;

import java.util.List;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.ViewHolder> {

    private List<Hourly> mWeatherHourlyList;
    private Context mContext;

    public HourlyAdapter(List<Hourly> weatherHourlyList, Context context) {
        mWeatherHourlyList = weatherHourlyList;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_fragment_weather_detail_hourly_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(v -> {
            // TODO 点击进入详情页，因为有很多字段没有展示
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hourly weatherHourly = mWeatherHourlyList.get(position);
        holder.mTempTextView.setText(weatherHourly.getTemp());
        holder.mDescriptionTextView.setText(weatherHourly.getDescription());
        holder.mTimeTextView.setText(weatherHourly.getTime());
        ImageParam param = new ImageParam.Builder()
                .context(mContext)
                .imageView(holder.mImageView)
                .url(Constants.WEATHER_ICON_URL + weatherHourly.getCode() + ".png")
                .error(mContext.getResources().getDrawable(R.drawable.weather_ic_na))
                .placeholder(mContext.getResources().getDrawable(R.drawable.weather_ic_na))
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
            mTempTextView = itemView.findViewById(R.id.weather_fragment_weather_detail_hourly_item_temp);
            mDescriptionTextView = itemView.findViewById(R.id.weather_fragment_weather_detail_hourly_item_desc);
            mTimeTextView = itemView.findViewById(R.id.weather_fragment_weather_detail_hourly_item_time);
            mImageView = itemView.findViewById(R.id.weather_fragment_weather_detail_hourly_item_icon);
        }
    }
}
